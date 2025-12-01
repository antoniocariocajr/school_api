package com.school.controllers.dto.paymentplan;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record PaymentPlanCreateDto(
        @NotNull UUID studentId,
        @NotNull UUID schoolTermId,
        @Positive BigDecimal totalValue,
        @PositiveOrZero BigDecimal discount,
        @Min(1) @Max(12) Integer installmentCount,
        @Min(1) @Max(31) Integer dueDay) {
}
