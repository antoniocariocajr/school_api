package com.school.services.mapper;

import com.school.controllers.dto.schoolclass.SchoolClassCreateDto;
import com.school.controllers.dto.schoolclass.SchoolClassDto;
import com.school.controllers.dto.schoolclass.SchoolClassUpdateDto;
import com.school.persistence.entities.Discipline;
import com.school.persistence.entities.SchoolClass;
import com.school.persistence.entities.SchoolTerm;
import com.school.persistence.entities.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchoolClassMapper {

    private final DisciplineMapper disciplineMapper;
    private final SchoolTermMapper schoolTermMapper;
    private final TeacherMapper teacherMapper;

    public SchoolClassDto toDto(SchoolClass schoolClass) {
        return new SchoolClassDto(
                schoolClass.getId(),
                disciplineMapper.toDto(schoolClass.getDiscipline()),
                schoolTermMapper.toDto(schoolClass.getSchoolTerm()),
                schoolClass.getTeacher() != null ? teacherMapper.toDto(schoolClass.getTeacher()) : null,
                schoolClass.getCode(),
                schoolClass.getMaxStudents(),
                schoolClass.getStatus());
    }

    public SchoolClass toEntity(Discipline discipline, SchoolTerm schoolTerm, Teacher teacher,
            SchoolClassCreateDto dto) {
        return SchoolClass.builder()
                .discipline(discipline)
                .schoolTerm(schoolTerm)
                .teacher(teacher)
                .code(dto.code())
                .maxStudents(dto.maxStudents() != null ? dto.maxStudents() : 50)
                .status(SchoolClass.Status.ACTIVE)
                .build();
    }

    public void updateEntity(SchoolClass schoolClass, Teacher teacher, SchoolClassUpdateDto dto) {
        if (dto.teacherId() != null) {
            schoolClass.setTeacher(teacher);
        }
        if (dto.code() != null) {
            schoolClass.setCode(dto.code());
        }
        if (dto.maxStudents() != null) {
            schoolClass.setMaxStudents(dto.maxStudents());
        }
        if (dto.status() != null) {
            schoolClass.setStatus(dto.status());
        }
    }
}
