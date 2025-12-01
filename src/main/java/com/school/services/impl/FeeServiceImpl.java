package com.school.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.school.controllers.dto.fee.FeeDto;
import com.school.controllers.dto.fee.FeePayDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Fee;
import com.school.persistence.repositories.FeeRepository;
import com.school.services.FeeService;
import com.school.services.mapper.PaymentPlanMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final FeeRepository repo;
    private final PaymentPlanMapper mapper;

    @Override
    @Transactional
    public FeeDto payInstallment(UUID feeId, FeePayDto dto) {
        Fee fee = repo.findById(feeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (fee.getStatus() == Fee.Status.PAID)
            throw new BusinessException("Parcela já paga");

        fee.pay(dto.paidDate());
        repo.save(fee);
        return mapper.tofeeDto(fee);
    }

    @Override
    public List<FeeDto> listByPlan(UUID paymentPlanId) {
        return repo.findByPaymentPlanId(paymentPlanId)
                .stream().map(mapper::tofeeDto).toList();
    }

    @Override
    public void generateOverdueFines() {
        List<Fee> overdue = repo.findByStatusAndDueDateBefore(Fee.Status.PENDING, LocalDate.now());
        overdue.forEach(f -> {
            BigDecimal fine = f.getOriginalValue().multiply(BigDecimal.valueOf(0.02)); // 2 %
            f.setFine(fine);
            f.setStatus(Fee.Status.OVERDUE);
            repo.save(f);
        });
    }

}
