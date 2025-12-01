package com.school.controllers.dto.schoolclass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record SchoolClassCreateDto(
        @NotNull UUID disciplineId,
        @NotNull UUID schoolTermId,
        UUID teacherId,
        @NotBlank @Size(max = 30) String code,
        Integer maxStudents) {
}
