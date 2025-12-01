package com.school.services;

import com.school.controllers.dto.student.StudentCreateDto;
import com.school.controllers.dto.student.StudentDto;
import com.school.controllers.dto.student.StudentUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StudentService {
    StudentDto create(StudentCreateDto dto);

    StudentDto update(UUID id, StudentUpdateDto dto);

    StudentDto findById(UUID id);

    Page<StudentDto> findAll(Pageable pageable);

    void delete(UUID id);
}
