package com.school.services.mapper;

import org.springframework.stereotype.Component;

import com.school.controllers.dto.person.PersonDto;
import com.school.controllers.dto.teacher.TeacherCreateDto;
import com.school.controllers.dto.teacher.TeacherDto;
import com.school.controllers.dto.teacher.TeacherUpdateDto;
import com.school.persistence.entities.Person;
import com.school.persistence.entities.Teacher;

@Component
public class TeacherMapper {
    public TeacherDto toDto(Teacher teacher) {
        var personDto = PersonDto.toDto(teacher.getPerson());
        return new TeacherDto(
                teacher.getId(),
                personDto,
                teacher.getRegistrationCode(),
                teacher.getHireDate(),
                teacher.getLeaveDate(),
                teacher.getActive());
    }

    public Teacher toEntity(Person person, TeacherCreateDto teacherCreateDto) {
        return Teacher.builder()
                .person(person)
                .registrationCode(teacherCreateDto.registrationCode())
                .hireDate(teacherCreateDto.hireDate())
                .build();
    }

    public void updateEntity(Teacher teacher, TeacherUpdateDto teacherUpdateDto) {
        if (teacherUpdateDto.registrationCode() != null) {
            teacher.setRegistrationCode(teacherUpdateDto.registrationCode());
        }
        if (teacherUpdateDto.hireDate() != null) {
            teacher.setHireDate(teacherUpdateDto.hireDate());
        }
        if (teacherUpdateDto.leaveDate() != null) {
            teacher.setLeaveDate(teacherUpdateDto.leaveDate());
        }
        if (teacherUpdateDto.active() != null) {
            teacher.setActive(teacherUpdateDto.active());
        }

    }
}
