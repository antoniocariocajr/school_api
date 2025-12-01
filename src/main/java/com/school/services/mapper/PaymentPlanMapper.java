package com.school.services.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.school.controllers.dto.fee.FeeDto;
import com.school.controllers.dto.paymentplan.PaymentPlanCreateDto;
import com.school.controllers.dto.paymentplan.PaymentPlanDto;
import com.school.persistence.entities.Fee;
import com.school.persistence.entities.PaymentPlan;
import com.school.persistence.entities.SchoolTerm;
import com.school.persistence.entities.Student;

@Component
public class PaymentPlanMapper {

    public PaymentPlanDto toDto(PaymentPlan pp) {
        List<FeeDto> fees = pp.getFees().stream()
                .map(this::tofeeDto)
                .toList();
        return new PaymentPlanDto(
                pp.getId(),
                pp.getStudent().getId(),
                pp.getStudent().getPerson().getName(),
                pp.getSchoolTerm().getId(),
                pp.getSchoolTerm().getCode(),
                pp.getTotalValue(),
                pp.getDiscount(),
                pp.getInstallmentCount(),
                pp.getDueDay(),
                pp.getStatus(),
                fees);
    }

    public PaymentPlan toEntity(PaymentPlanCreateDto dto, Student student, SchoolTerm term) {
        return PaymentPlan.builder()
                .student(student)
                .schoolTerm(term)
                .totalValue(dto.totalValue())
                .discount(dto.discount())
                .installmentCount(dto.installmentCount())
                .dueDay(dto.dueDay())
                .status(PaymentPlan.Status.ACTIVE)
                .build();
    }

    public FeeDto tofeeDto(Fee fee) {
        return new FeeDto(
                fee.getId(),
                fee.getInstallmentNumber(),
                fee.getDueDate(),
                fee.getOriginalValue(),
                fee.getFine(),
                fee.getTotalValue(),
                fee.getPaidDate(),
                fee.getStatus());
    }
}
