package com.school.services.impl;

import com.school.controllers.dto.person.PersonCreateDto;
import com.school.controllers.dto.person.PersonDto;
import com.school.controllers.dto.person.PersonUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Person;
import com.school.persistence.repositories.PersonRepository;
import com.school.persistence.storage.FileStorage;
import com.school.services.PersonService;
import com.school.services.mapper.PersonMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl  implements PersonService {

    private final PersonRepository repo;
    private final PersonMapper mapper;
    private final FileStorage fileStorage;

    /* ---------- CRUD ---------- */
    @Override
    public Page<PersonDto> findAll(Pageable page) {
        return repo.findAll(page).map(mapper::toDto);
    }

    @Override
    public PersonDto findById(UUID id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public PersonDto create(PersonCreateDto dto) {
        if (repo.existsByEmail(dto.email()))
            throw new BusinessException("Email já cadastrado");
        if (repo.existsByCpf(dto.cpf()))
            throw new BusinessException("CPF já cadastrado");

        Person saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public PersonDto update(UUID id, PersonUpdateDto dto) {
        Person p = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (dto.name() != null) p.setName(dto.name());
        if (dto.phone() != null) p.setPhone(dto.phone());
        return mapper.toDto(repo.save(p));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        repo.deleteById(id);
    }

    /* ---------- UPLOAD DE FOTO ---------- */
    @Override
    @Transactional
    public void uploadPicture(UUID personId, MultipartFile file) {
        Person p = repo.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String key = fileStorage.save(file); // devolve path/chave S3 ou disco
        p.setPictureKey(key);                // novo campo na Person
        repo.save(p);
    }

    @Override
    public Resource getPicture(UUID personId) {
        Person p = repo.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (p.getPictureKey() == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return fileStorage.load(p.getPictureKey());
    }
}
