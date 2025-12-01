package com.school.services.impl;

import com.school.controllers.dto.enrollment.EnrollmentCreateDto;
import com.school.controllers.dto.enrollment.EnrollmentDto;
import com.school.controllers.dto.enrollment.EnrollmentUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.SchoolClass;
import com.school.persistence.entities.Student;
import com.school.persistence.repositories.EnrollmentRepository;
import com.school.persistence.repositories.SchoolClassRepository;
import com.school.persistence.repositories.StudentRepository;
import com.school.services.EnrollmentService;
import com.school.services.mapper.EnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    @Transactional
    public EnrollmentDto create(EnrollmentCreateDto dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new BusinessException("Student not found with ID: " + dto.studentId()));

        SchoolClass schoolClass = schoolClassRepository.findById(dto.schoolClassId())
                .orElseThrow(() -> new BusinessException("School Class not found with ID: " + dto.schoolClassId()));

        if (enrollmentRepository.existsByStudentIdAndSchoolClassId(dto.studentId(), dto.schoolClassId())) {
            throw new BusinessException("Student is already enrolled in this class.");
        }

        Enrollment enrollment = enrollmentMapper.toEntity(student, schoolClass, dto);
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    @Transactional
    public EnrollmentDto update(UUID id, EnrollmentUpdateDto dto) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Enrollment not found with ID: " + id));

        enrollmentMapper.updateEntity(enrollment, dto);
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentDto findById(UUID id) {
        return enrollmentRepository.findById(id)
                .map(enrollmentMapper::toDto)
                .orElseThrow(() -> new BusinessException("Enrollment not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentDto> findAll(Pageable pageable) {
        return enrollmentRepository.findAll(pageable).map(enrollmentMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Enrollment not found with ID: " + id));

        enrollment.setStatus(Enrollment.Status.CANCELED);
        enrollmentRepository.save(enrollment);
    }
}
