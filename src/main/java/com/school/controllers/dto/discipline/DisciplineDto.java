package com.school.controllers.dto.discipline;

import com.school.controllers.dto.course.CourseDto;
import java.util.UUID;

public record DisciplineDto(
        UUID id,
        CourseDto course,
        String name,
        String code,
        Integer totalHours,
        String syllabus) {
}
