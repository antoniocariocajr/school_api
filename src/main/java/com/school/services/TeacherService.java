package com.school.services;

import com.school.controllers.dto.teacher.TeacherCreateDto;
import com.school.controllers.dto.teacher.TeacherDto;
import com.school.controllers.dto.teacher.TeacherUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TeacherService {
    TeacherDto create(TeacherCreateDto dto);

    TeacherDto update(UUID id, TeacherUpdateDto dto);

    TeacherDto findById(UUID id);

    Page<TeacherDto> findAll(Pageable pageable);

    void delete(UUID id);
}
