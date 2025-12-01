package com.school.controllers.dto.student;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record StudentCreateDto(
        @NotNull UUID personId,
        String enrollmentNumber,
        @NotNull LocalDate entryDate,
        String originSchool) {
}
