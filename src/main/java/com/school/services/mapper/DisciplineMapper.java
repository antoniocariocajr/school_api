package com.school.services.mapper;

import com.school.controllers.dto.discipline.DisciplineCreateDto;
import com.school.controllers.dto.discipline.DisciplineDto;
import com.school.controllers.dto.discipline.DisciplineUpdateDto;
import com.school.persistence.entities.Course;
import com.school.persistence.entities.Discipline;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DisciplineMapper {

    private final CourseMapper courseMapper;

    public DisciplineDto toDto(Discipline discipline) {
        return new DisciplineDto(
                discipline.getId(),
                courseMapper.toDto(discipline.getCourse()),
                discipline.getName(),
                discipline.getCode(),
                discipline.getTotalHours(),
                discipline.getSyllabus());
    }

    public Discipline toEntity(Course course, DisciplineCreateDto dto) {
        return Discipline.builder()
                .course(course)
                .name(dto.name())
                .code(dto.code())
                .totalHours(dto.totalHours())
                .syllabus(dto.syllabus())
                .build();
    }

    public void updateEntity(Discipline discipline, DisciplineUpdateDto dto) {
        if (dto.name() != null) {
            discipline.setName(dto.name());
        }
        if (dto.code() != null) {
            discipline.setCode(dto.code());
        }
        if (dto.totalHours() != null) {
            discipline.setTotalHours(dto.totalHours());
        }
        if (dto.syllabus() != null) {
            discipline.setSyllabus(dto.syllabus());
        }
    }
}
