package com.school.services.impl;

import com.school.controllers.dto.course.CourseCreateDto;
import com.school.controllers.dto.course.CourseDto;
import com.school.controllers.dto.course.CourseUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Course;
import com.school.persistence.repositories.CourseRepository;
import com.school.services.CourseService;
import com.school.services.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional
    public CourseDto create(CourseCreateDto dto) {
        if (courseRepository.existsByCode(dto.code())) {
            throw new BusinessException("Course with code " + dto.code() + " already exists.");
        }

        Course course = courseMapper.toEntity(dto);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional
    public CourseDto update(UUID id, CourseUpdateDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Course not found with ID: " + id));

        if (dto.code() != null && !dto.code().equals(course.getCode()) && courseRepository.existsByCode(dto.code())) {
            throw new BusinessException("Course with code " + dto.code() + " already exists.");
        }

        courseMapper.updateEntity(course, dto);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto findById(UUID id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDto)
                .orElseThrow(() -> new BusinessException("Course not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDto> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!courseRepository.existsById(id)) {
            throw new BusinessException("Course not found with ID: " + id);
        }
        courseRepository.deleteById(id);
    }
}
