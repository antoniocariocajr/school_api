package com.school.controllers.dto.enrollment;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record EnrollmentCreateDto(
        @NotNull UUID studentId,
        @NotNull UUID schoolClassId) {
}
