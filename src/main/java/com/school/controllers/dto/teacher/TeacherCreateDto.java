package com.school.controllers.dto.teacher;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record TeacherCreateDto(
        @NotNull UUID personId,
        String registrationCode,
        @NotNull LocalDate hireDate) {
}
