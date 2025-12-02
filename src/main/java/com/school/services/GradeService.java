package com.school.services;

import com.school.controllers.dto.grade.GradeCreateDto;
import com.school.controllers.dto.grade.GradeDto;
import com.school.controllers.dto.grade.GradeUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GradeService {
    GradeDto create(GradeCreateDto dto);

    GradeDto update(UUID id, GradeUpdateDto dto);

    GradeDto findById(UUID id);

    Page<GradeDto> findAll(Pageable pageable);

    void delete(UUID id);
}
