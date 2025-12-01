package com.school.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.school.controllers.dto.schoolterm.SchoolTermCreateDto;
import com.school.controllers.dto.schoolterm.SchoolTermDto;
import com.school.controllers.dto.schoolterm.SchoolTermUpdateDto;
import com.school.persistence.entities.SchoolTerm;
import com.school.persistence.repositories.SchoolTermRepository;
import com.school.services.SchoolTermService;
import com.school.services.mapper.SchoolTermMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchoolTermServiceImpl implements SchoolTermService {

    private final SchoolTermRepository schoolTermRepository;
    private final SchoolTermMapper schoolTermMapper;

    @Override
    public SchoolTermDto createSchoolTerm(SchoolTermCreateDto schoolTermCreateDto) {
        SchoolTerm schoolTerm = schoolTermMapper.toEntity(schoolTermCreateDto);
        schoolTermRepository.save(schoolTerm);
        return schoolTermMapper.toDto(schoolTerm);
    }

    @Override
    public SchoolTermDto updateSchoolTerm(UUID id, SchoolTermUpdateDto schoolTermUpdateDto) {
        SchoolTerm schoolTerm = schoolTermRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School Term not found"));
        schoolTermMapper.updateEntity(schoolTerm, schoolTermUpdateDto);
        schoolTermRepository.save(schoolTerm);
        return schoolTermMapper.toDto(schoolTerm);
    }

    @Override
    public void deleteSchoolTerm(UUID id) {
        schoolTermRepository.deleteById(id);
    }

    @Override
    public SchoolTermDto getSchoolTermById(UUID id) {
        SchoolTerm schoolTerm = schoolTermRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School Term not found"));
        return schoolTermMapper.toDto(schoolTerm);
    }

    @Override
    public Page<SchoolTermDto> getAllSchoolTerms(Pageable pageable) {
        Page<SchoolTerm> schoolTerms = schoolTermRepository.findAll(pageable);
        return schoolTerms.map(schoolTermMapper::toDto);
    }

}
