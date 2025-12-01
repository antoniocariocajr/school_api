package com.school.controllers.dto.fee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.school.persistence.entities.Fee;

public record FeeDto(
        UUID id,
        Integer installmentNumber,
        LocalDate dueDate,
        BigDecimal originalValue,
        BigDecimal fine,
        BigDecimal totalValue,
        LocalDate paidDate,
        Fee.Status status) {
}
