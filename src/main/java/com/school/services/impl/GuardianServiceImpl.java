package com.school.services.impl;

import com.school.controllers.dto.guardian.GuardianCreateDto;
import com.school.controllers.dto.guardian.GuardianDto;
import com.school.controllers.dto.guardian.GuardianUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Guardian;
import com.school.persistence.entities.Person;
import com.school.persistence.repositories.GuardianRepository;
import com.school.persistence.repositories.PersonRepository;
import com.school.services.GuardianService;
import com.school.services.mapper.GuardianMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository guardianRepository;
    private final PersonRepository personRepository;
    private final GuardianMapper guardianMapper;

    @Override
    @Transactional
    public GuardianDto create(GuardianCreateDto dto) {
        Person person = personRepository.findById(dto.personId())
                .orElseThrow(() -> new BusinessException("Person not found with ID: " + dto.personId()));

        if (guardianRepository.existsByPersonId(dto.personId())) {
            throw new BusinessException("This person is already a guardian.");
        }

        Guardian guardian = guardianMapper.toEntity(person, dto);
        guardian = guardianRepository.save(guardian);
        return guardianMapper.toDto(guardian);
    }

    @Override
    @Transactional
    public GuardianDto update(UUID id, GuardianUpdateDto dto) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Guardian not found with ID: " + id));

        guardianMapper.updateEntity(guardian, dto);
        guardian = guardianRepository.save(guardian);
        return guardianMapper.toDto(guardian);
    }

    @Override
    @Transactional(readOnly = true)
    public GuardianDto findById(UUID id) {
        return guardianRepository.findById(id)
                .map(guardianMapper::toDto)
                .orElseThrow(() -> new BusinessException("Guardian not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GuardianDto> findAll(Pageable pageable) {
        return guardianRepository.findAll(pageable).map(guardianMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Guardian not found with ID: " + id));
        guardian.setActive(false); // Soft delete
        guardianRepository.save(guardian);
    }
}
