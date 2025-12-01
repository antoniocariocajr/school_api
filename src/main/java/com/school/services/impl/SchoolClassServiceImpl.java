package com.school.services.impl;

import com.school.controllers.dto.schoolclass.SchoolClassCreateDto;
import com.school.controllers.dto.schoolclass.SchoolClassDto;
import com.school.controllers.dto.schoolclass.SchoolClassUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Discipline;
import com.school.persistence.entities.SchoolClass;
import com.school.persistence.entities.SchoolTerm;
import com.school.persistence.entities.Teacher;
import com.school.persistence.repositories.DisciplineRepository;
import com.school.persistence.repositories.SchoolClassRepository;
import com.school.persistence.repositories.SchoolTermRepository;
import com.school.persistence.repositories.TeacherRepository;
import com.school.services.SchoolClassService;
import com.school.services.mapper.SchoolClassMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final DisciplineRepository disciplineRepository;
    private final SchoolTermRepository schoolTermRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassMapper schoolClassMapper;

    @Override
    @Transactional
    public SchoolClassDto create(SchoolClassCreateDto dto) {
        Discipline discipline = disciplineRepository.findById(dto.disciplineId())
                .orElseThrow(() -> new BusinessException("Discipline not found with ID: " + dto.disciplineId()));

        SchoolTerm schoolTerm = schoolTermRepository.findById(dto.schoolTermId())
                .orElseThrow(() -> new BusinessException("School Term not found with ID: " + dto.schoolTermId()));

        Teacher teacher = null;
        if (dto.teacherId() != null) {
            teacher = teacherRepository.findById(dto.teacherId())
                    .orElseThrow(() -> new BusinessException("Teacher not found with ID: " + dto.teacherId()));
        }

        if (schoolClassRepository.existsByCode(dto.code())) {
            throw new BusinessException("School Class with code " + dto.code() + " already exists.");
        }

        SchoolClass schoolClass = schoolClassMapper.toEntity(discipline, schoolTerm, teacher, dto);
        schoolClass = schoolClassRepository.save(schoolClass);
        return schoolClassMapper.toDto(schoolClass);
    }

    @Override
    @Transactional
    public SchoolClassDto update(UUID id, SchoolClassUpdateDto dto) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new BusinessException("School Class not found with ID: " + id));

        Teacher teacher = schoolClass.getTeacher();
        if (dto.teacherId() != null) {
            teacher = teacherRepository.findById(dto.teacherId())
                    .orElseThrow(() -> new BusinessException("Teacher not found with ID: " + dto.teacherId()));
        }

        if (dto.code() != null && !dto.code().equals(schoolClass.getCode())
                && schoolClassRepository.existsByCode(dto.code())) {
            throw new BusinessException("School Class with code " + dto.code() + " already exists.");
        }

        schoolClassMapper.updateEntity(schoolClass, teacher, dto);
        schoolClass = schoolClassRepository.save(schoolClass);
        return schoolClassMapper.toDto(schoolClass);
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolClassDto findById(UUID id) {
        return schoolClassRepository.findById(id)
                .map(schoolClassMapper::toDto)
                .orElseThrow(() -> new BusinessException("School Class not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchoolClassDto> findAll(Pageable pageable) {
        return schoolClassRepository.findAll(pageable).map(schoolClassMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new BusinessException("School Class not found with ID: " + id));

        schoolClass.setStatus(SchoolClass.Status.CANCELED);
        schoolClassRepository.save(schoolClass);
    }
}
