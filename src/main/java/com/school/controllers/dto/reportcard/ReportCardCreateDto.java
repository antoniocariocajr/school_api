package com.school.controllers.dto.reportcard;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record ReportCardCreateDto(
        @NotNull UUID enrollmentId,
        @NotNull UUID schoolTermId,
        @PositiveOrZero BigDecimal finalGrade,
        @PositiveOrZero @Max(100) BigDecimal finalAttendance,
        String remarks) {
}
