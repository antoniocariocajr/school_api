package com.school.services.impl;

import com.school.controllers.dto.paymentplan.PaymentPlanCreateDto;
import com.school.controllers.dto.paymentplan.PaymentPlanDto;
import com.school.controllers.dto.paymentplan.PaymentPlanUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Fee.Status;
import com.school.persistence.entities.PaymentPlan;
import com.school.persistence.entities.SchoolTerm;
import com.school.persistence.entities.Student;
import com.school.persistence.repositories.PaymentPlanRepository;
import com.school.persistence.repositories.SchoolTermRepository;
import com.school.persistence.repositories.StudentRepository;
import com.school.services.PaymentPlanService;
import com.school.services.mapper.PaymentPlanMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentPlanServiceImpl implements PaymentPlanService {

    private final PaymentPlanRepository repo;
    private final PaymentPlanMapper mapper;
    private final StudentRepository studentRepo;
    private final SchoolTermRepository termRepo;

    @Override
    @Transactional
    public PaymentPlanDto create(PaymentPlanCreateDto dto) {
        Student student = studentRepo.findById(dto.studentId())
                .orElseThrow(() -> new BusinessException("Aluno não encontrado"));
        SchoolTerm term = termRepo.findById(dto.schoolTermId())
                .orElseThrow(() -> new BusinessException("Período letivo não encontrado"));

        if (repo.findByStudentIdAndSchoolTermIdAndStatus(student.getId(), term.getId(), PaymentPlan.Status.ACTIVE)
                .isPresent())
            throw new BusinessException("Já existe plano ativo para este aluno/termo");

        PaymentPlan plan = repo.save(mapper.toEntity(dto, student, term));
        plan.generateFees(); // cria as parcelas
        return mapper.toDto(repo.save(plan));
    }

    @Override
    public PaymentPlanDto findById(UUID id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<PaymentPlanDto> listByStudent(UUID studentId) {
        return repo.findByStudentId(studentId).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public PaymentPlanDto update(UUID id, PaymentPlanUpdateDto dto) {
        PaymentPlan plan = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (dto.discount() != null)
            plan.setDiscount(dto.discount());
        if (dto.dueDay() != null)
            plan.setDueDay(dto.dueDay());
        return mapper.toDto(repo.save(plan));
    }

    @Override
    @Transactional
    public void cancel(UUID id) {
        PaymentPlan plan = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        plan.setStatus(PaymentPlan.Status.CANCELED);
        plan.getFees().forEach(f -> f.setStatus(Status.WAIVED));
        repo.save(plan);
    }

    @Override
    public Page<PaymentPlanDto> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }
}