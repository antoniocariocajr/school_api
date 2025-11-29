package com.school.services.impl;

import com.school.persistence.entities.PaymentPlan;
import com.school.persistence.entities.SchoolTerm;
import com.school.persistence.entities.Student;
import com.school.persistence.repositories.PaymentPlanRepository;
import com.school.persistence.repositories.SchoolTermRepository;
import com.school.persistence.repositories.StudentRepository;
import com.school.services.PaymentPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentPlanServiceImpl implements PaymentPlanService {

    private final PaymentPlanRepository planRepository;
    private final SchoolTermRepository termRepository;
    private final StudentRepository studentRepository;

    @Transactional
    @Override
    public PaymentPlan createPlan(UUID studentId, UUID schoolTermId, BigDecimal totalValue, BigDecimal discount, Integer installmentCount, Integer dueDay) {
        if (installmentCount <= 0) throw new IllegalArgumentException("Parcelas devem ser > 0");

        Student student = studentRepository.findById(studentId).orElseThrow();
        SchoolTerm term = termRepository.findById(schoolTermId).orElseThrow();

        /* garante só 1 plano ativo por aluno/termo */
        planRepository.findByStudentIdAndSchoolTermIdAndStatus(studentId, schoolTermId, PaymentPlan.Status.ACTIVE)
                .ifPresent(p -> { throw new ResponseStatusException(HttpStatus.CONFLICT,"Já existe plano ativo para este aluno/termo"); });

        PaymentPlan plan = PaymentPlan.builder()
                .student(student)
                .schoolTerm(term)
                .totalValue(totalValue)
                .discount(discount)
                .installmentCount(installmentCount)
                .dueDay(dueDay)
                .status(PaymentPlan.Status.ACTIVE)
                .build();

        plan.generateFees(); // cria as linhas Fee
        return planRepository.save(plan);
    }
}
