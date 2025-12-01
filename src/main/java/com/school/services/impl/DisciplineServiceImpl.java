package com.school.services.impl;

import com.school.controllers.dto.discipline.DisciplineCreateDto;
import com.school.controllers.dto.discipline.DisciplineDto;
import com.school.controllers.dto.discipline.DisciplineUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Course;
import com.school.persistence.entities.Discipline;
import com.school.persistence.repositories.CourseRepository;
import com.school.persistence.repositories.DisciplineRepository;
import com.school.services.DisciplineService;
import com.school.services.mapper.DisciplineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository disciplineRepository;
    private final CourseRepository courseRepository;
    private final DisciplineMapper disciplineMapper;

    @Override
    @Transactional
    public DisciplineDto create(DisciplineCreateDto dto) {
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new BusinessException("Course not found with ID: " + dto.courseId()));

        if (dto.code() != null && disciplineRepository.existsByCode(dto.code())) {
            throw new BusinessException("Discipline with code " + dto.code() + " already exists.");
        }

        Discipline discipline = disciplineMapper.toEntity(course, dto);
        discipline = disciplineRepository.save(discipline);
        return disciplineMapper.toDto(discipline);
    }

    @Override
    @Transactional
    public DisciplineDto update(UUID id, DisciplineUpdateDto dto) {
        Discipline discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Discipline not found with ID: " + id));

        if (dto.code() != null && !dto.code().equals(discipline.getCode())
                && disciplineRepository.existsByCode(dto.code())) {
            throw new BusinessException("Discipline with code " + dto.code() + " already exists.");
        }

        disciplineMapper.updateEntity(discipline, dto);
        discipline = disciplineRepository.save(discipline);
        return disciplineMapper.toDto(discipline);
    }

    @Override
    @Transactional(readOnly = true)
    public DisciplineDto findById(UUID id) {
        return disciplineRepository.findById(id)
                .map(disciplineMapper::toDto)
                .orElseThrow(() -> new BusinessException("Discipline not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisciplineDto> findAll(Pageable pageable) {
        return disciplineRepository.findAll(pageable).map(disciplineMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!disciplineRepository.existsById(id)) {
            throw new BusinessException("Discipline not found with ID: " + id);
        }
        disciplineRepository.deleteById(id);
    }
}
