package com.school.services;

import com.school.persistence.entities.PaymentPlan;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentPlanService {
    PaymentPlan createPlan(UUID studentId,
                           UUID schoolTermId,
                           BigDecimal totalValue,
                           BigDecimal discount,
                           Integer installmentCount,
                           Integer dueDay
    );
}
