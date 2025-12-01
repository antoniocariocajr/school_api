package com.school.services.impl;

import com.school.controllers.dto.student.StudentCreateDto;
import com.school.controllers.dto.student.StudentDto;
import com.school.controllers.dto.student.StudentUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Person;
import com.school.persistence.entities.Student;
import com.school.persistence.repositories.PersonRepository;
import com.school.persistence.repositories.StudentRepository;
import com.school.services.StudentService;
import com.school.services.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentDto create(StudentCreateDto dto) {
        Person person = personRepository.findById(dto.personId())
                .orElseThrow(() -> new BusinessException("Person not found with ID: " + dto.personId()));

        if (studentRepository.existsByPersonId(dto.personId())) {
            throw new BusinessException("This person is already a student.");
        }

        Student student = studentMapper.toEntity(person, dto);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional
    public StudentDto update(UUID id, StudentUpdateDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Student not found with ID: " + id));

        studentMapper.updateEntity(student, dto);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDto findById(UUID id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new BusinessException("Student not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentDto> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Student not found with ID: " + id));
        student.setActive(false); // Soft delete
        studentRepository.save(student);
    }
}
