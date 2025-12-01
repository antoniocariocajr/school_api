package com.school.services.mapper;

import com.school.controllers.dto.course.CourseCreateDto;
import com.school.controllers.dto.course.CourseDto;
import com.school.controllers.dto.course.CourseUpdateDto;
import com.school.persistence.entities.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDto toDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getName(),
                course.getCode(),
                course.getTotalHours(),
                course.getSyllabus(),
                course.getMinimumGrade());
    }

    public Course toEntity(CourseCreateDto dto) {
        return Course.builder()
                .name(dto.name())
                .code(dto.code())
                .totalHours(dto.totalHours())
                .syllabus(dto.syllabus())
                .minimumGrade(dto.minimumGrade())
                .build();
    }

    public void updateEntity(Course course, CourseUpdateDto dto) {
        if (dto.name() != null) {
            course.setName(dto.name());
        }
        if (dto.code() != null) {
            course.setCode(dto.code());
        }
        if (dto.totalHours() != null) {
            course.setTotalHours(dto.totalHours());
        }
        if (dto.syllabus() != null) {
            course.setSyllabus(dto.syllabus());
        }
        if (dto.minimumGrade() != null) {
            course.setMinimumGrade(dto.minimumGrade());
        }
    }
}
