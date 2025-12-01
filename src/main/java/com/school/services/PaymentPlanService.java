package com.school.services;

import com.school.controllers.dto.paymentplan.PaymentPlanCreateDto;
import com.school.controllers.dto.paymentplan.PaymentPlanDto;
import com.school.controllers.dto.paymentplan.PaymentPlanUpdateDto;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentPlanService {
    PaymentPlanDto create(PaymentPlanCreateDto dto);

    PaymentPlanDto findById(UUID id);

    List<PaymentPlanDto> listByStudent(UUID studentId);

    PaymentPlanDto update(UUID id, PaymentPlanUpdateDto dto);

    void cancel(UUID id);

    Page<PaymentPlanDto> list(Pageable pageable);
}
