package com.school.services;

import java.util.List;
import java.util.UUID;

import com.school.controllers.dto.fee.FeeDto;
import com.school.controllers.dto.fee.FeePayDto;

public interface FeeService {
    FeeDto payInstallment(UUID feeId, FeePayDto dto);

    List<FeeDto> listByPlan(UUID paymentPlanId);

    void generateOverdueFines();
}
