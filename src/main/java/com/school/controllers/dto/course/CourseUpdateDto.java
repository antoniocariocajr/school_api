package com.school.controllers.dto.course;

import jakarta.validation.constraints.Size;

public record CourseUpdateDto(
        @Size(max = 120) String name,
        @Size(max = 20) String code,
        Integer totalHours,
        String syllabus,
        Integer minimumGrade) {
}
