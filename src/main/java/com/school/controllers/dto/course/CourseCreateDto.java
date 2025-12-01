package com.school.controllers.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseCreateDto(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 20) String code,
        @NotNull Integer totalHours,
        String syllabus,
        @NotNull Integer minimumGrade) {
}
