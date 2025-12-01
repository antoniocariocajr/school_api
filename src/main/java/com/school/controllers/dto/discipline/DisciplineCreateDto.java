package com.school.controllers.dto.discipline;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record DisciplineCreateDto(
        @NotNull UUID courseId,
        @NotBlank @Size(max = 120) String name,
        @Size(max = 30) String code,
        Integer totalHours,
        String syllabus) {
}
