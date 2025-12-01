package com.school.controllers.dto.discipline;

import jakarta.validation.constraints.Size;

public record DisciplineUpdateDto(
        @Size(max = 120) String name,
        @Size(max = 30) String code,
        Integer totalHours,
        String syllabus) {
}
