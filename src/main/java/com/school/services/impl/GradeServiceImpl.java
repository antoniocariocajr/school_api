package com.school.services.impl;

import com.school.controllers.dto.grade.GradeCreateDto;
import com.school.controllers.dto.grade.GradeDto;
import com.school.controllers.dto.grade.GradeUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.Grade;
import com.school.persistence.repositories.EnrollmentRepository;
import com.school.persistence.repositories.GradeRepository;
import com.school.services.GradeService;
import com.school.services.mapper.GradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeMapper gradeMapper;

    @Override
    @Transactional
    public GradeDto create(GradeCreateDto dto) {
        Enrollment enrollment = enrollmentRepository.findById(dto.enrollmentId())
                .orElseThrow(() -> new BusinessException("Enrollment not found with ID: " + dto.enrollmentId()));

        Grade grade = gradeMapper.toEntity(enrollment, dto);
        grade = gradeRepository.save(grade);
        return gradeMapper.toDto(grade);
    }

    @Override
    @Transactional
    public GradeDto update(UUID id, GradeUpdateDto dto) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Grade not found with ID: " + id));

        gradeMapper.updateEntity(grade, dto);
        grade = gradeRepository.save(grade);
        return gradeMapper.toDto(grade);
    }

    @Override
    @Transactional(readOnly = true)
    public GradeDto findById(UUID id) {
        return gradeRepository.findById(id)
                .map(gradeMapper::toDto)
                .orElseThrow(() -> new BusinessException("Grade not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GradeDto> findAll(Pageable pageable) {
        return gradeRepository.findAll(pageable).map(gradeMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!gradeRepository.existsById(id)) {
            throw new BusinessException("Grade not found with ID: " + id);
        }
        gradeRepository.deleteById(id);
    }
}
