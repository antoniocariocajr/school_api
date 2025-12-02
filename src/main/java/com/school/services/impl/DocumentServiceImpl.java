package com.school.services.impl;

import com.school.controllers.dto.document.DocumentCreateDto;
import com.school.controllers.dto.document.DocumentDto;
import com.school.persistence.entities.Document;
import com.school.persistence.entities.Person;
import com.school.persistence.repositories.DocumentRepository;
import com.school.persistence.repositories.PersonRepository;
import com.school.persistence.storage.FileStorage;
import com.school.services.DocumentService;
import com.school.services.mapper.DocumentMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repo;
    private final PersonRepository personRepo;
    private final DocumentMapper mapper;
    private final FileStorage fileStorage;

    @Override
    public DocumentDto upload(UUID personId, DocumentCreateDto dto, String uploadedBy) {
        Person person = personRepo.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String key = fileStorage.save(dto.file());
        Document doc = repo.save(mapper.toEntity(dto, person, key, uploadedBy));
        return mapper.toDto(doc);
    }

    @Override
    public List<DocumentDto> listByPerson(UUID personId) {
        if (!personRepo.existsById(personId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return repo.findByPersonId(personId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<DocumentDto> listByPersonAndType(UUID personId, Document.Type type) {
        return repo.findByPersonIdAndType(personId, type).stream().map(mapper::toDto).toList();
    }

    @Override
    public Resource download(UUID documentId) {
        Document doc = repo.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return fileStorage.load(doc.getStorageKey());
    }

    @Override
    public void delete(UUID documentId) {
        Document doc = repo.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        fileStorage.delete(doc.getStorageKey());
        repo.delete(doc);
    }
}