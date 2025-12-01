package com.school.controllers.dto.paymentplan;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PaymentPlanUpdateDto(
        BigDecimal discount,
        @Min(1) @Max(31) Integer dueDay) {

}
