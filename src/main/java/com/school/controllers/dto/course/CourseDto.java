package com.school.controllers.dto.course;

import java.util.UUID;

public record CourseDto(
        UUID id,
        String name,
        String code,
        Integer totalHours,
        String syllabus,
        Integer minimumGrade) {
}
