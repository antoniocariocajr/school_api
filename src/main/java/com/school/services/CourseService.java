package com.school.services;

import com.school.controllers.dto.course.CourseCreateDto;
import com.school.controllers.dto.course.CourseDto;
import com.school.controllers.dto.course.CourseUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseService {
    CourseDto create(CourseCreateDto dto);

    CourseDto update(UUID id, CourseUpdateDto dto);

    CourseDto findById(UUID id);

    Page<CourseDto> findAll(Pageable pageable);

    void delete(UUID id);
}
