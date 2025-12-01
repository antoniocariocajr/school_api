package com.school.controllers.dto.paymentplan;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.school.controllers.dto.fee.FeeDto;
import com.school.persistence.entities.PaymentPlan;

public record PaymentPlanDto(
        UUID id,
        UUID studentId,
        String studentName,
        UUID schoolTermId,
        String schoolTermCode,
        BigDecimal totalValue,
        BigDecimal discount,
        Integer installmentCount,
        Integer dueDay,
        PaymentPlan.Status status,
        List<FeeDto> fees) {
}
