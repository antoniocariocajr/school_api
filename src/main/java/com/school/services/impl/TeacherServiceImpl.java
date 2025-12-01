package com.school.services.impl;

import com.school.controllers.dto.teacher.TeacherCreateDto;
import com.school.controllers.dto.teacher.TeacherDto;
import com.school.controllers.dto.teacher.TeacherUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Person;
import com.school.persistence.entities.Teacher;
import com.school.persistence.repositories.PersonRepository;
import com.school.persistence.repositories.TeacherRepository;
import com.school.services.TeacherService;
import com.school.services.mapper.TeacherMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final PersonRepository personRepository;
    private final TeacherMapper teacherMapper;

    @Override
    @Transactional
    public TeacherDto create(TeacherCreateDto dto) {
        Person person = personRepository.findById(dto.personId())
                .orElseThrow(() -> new BusinessException("Person not found with ID: " + dto.personId()));
        if (teacherRepository.existsByPersonId(person.getId())) {
            throw new BusinessException("This person is already a teacher.");
        }
        Teacher teacher = teacherMapper.toEntity(person, dto);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    @Override
    @Transactional
    public TeacherDto update(UUID id, TeacherUpdateDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Teacher not found with ID: " + id));

        teacherMapper.updateEntity(teacher, dto);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherDto findById(UUID id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::toDto)
                .orElseThrow(() -> new BusinessException("Teacher not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeacherDto> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable).map(teacherMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Teacher not found with ID: " + id));
        teacher.setActive(false); // Soft delete
        teacherRepository.save(teacher);
    }
}
