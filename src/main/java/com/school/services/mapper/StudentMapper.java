package com.school.services.mapper;

import com.school.controllers.dto.person.PersonDto;
import com.school.controllers.dto.student.StudentCreateDto;
import com.school.controllers.dto.student.StudentDto;
import com.school.controllers.dto.student.StudentUpdateDto;
import com.school.persistence.entities.Person;
import com.school.persistence.entities.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDto toDto(Student student) {
        PersonDto personDto = PersonDto.toDto(student.getPerson());
        return new StudentDto(
                student.getId(),
                personDto,
                student.getEnrollmentNumber(),
                student.getEntryDate(),
                student.getExitDate(),
                student.getOriginSchool(),
                student.getActive());
    }

    public Student toEntity(Person person, StudentCreateDto dto) {
        return Student.builder()
                .person(person)
                .enrollmentNumber(dto.enrollmentNumber())
                .entryDate(dto.entryDate())
                .originSchool(dto.originSchool())
                .active(true)
                .build();
    }

    public void updateEntity(Student student, StudentUpdateDto dto) {
        if (dto.enrollmentNumber() != null) {
            student.setEnrollmentNumber(dto.enrollmentNumber());
        }
        if (dto.entryDate() != null) {
            student.setEntryDate(dto.entryDate());
        }
        if (dto.exitDate() != null) {
            student.setExitDate(dto.exitDate());
        }
        if (dto.originSchool() != null) {
            student.setOriginSchool(dto.originSchool());
        }
        if (dto.active() != null) {
            student.setActive(dto.active());
        }
    }
}
