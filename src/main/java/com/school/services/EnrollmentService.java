package com.school.services;

import com.school.controllers.dto.enrollment.EnrollmentCreateDto;
import com.school.controllers.dto.enrollment.EnrollmentDto;
import com.school.controllers.dto.enrollment.EnrollmentUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EnrollmentService {
    EnrollmentDto create(EnrollmentCreateDto dto);

    EnrollmentDto update(UUID id, EnrollmentUpdateDto dto);

    EnrollmentDto findById(UUID id);

    Page<EnrollmentDto> findAll(Pageable pageable);

    void delete(UUID id);
}
