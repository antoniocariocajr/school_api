package com.school.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.controllers.dto.paymentplan.PaymentPlanCreateDto;
import com.school.controllers.dto.paymentplan.PaymentPlanDto;
import com.school.controllers.dto.paymentplan.PaymentPlanUpdateDto;
import com.school.services.PaymentPlanService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment-plans")
@RequiredArgsConstructor
@Tag(name = "Payment Plans", description = "Gestão de planos de pagamento")
public class PaymentPlanController {

    private final PaymentPlanService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('FINANCIAL')")
    public PaymentPlanDto create(@Valid @RequestBody PaymentPlanCreateDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public PaymentPlanDto find(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping
    public Page<PaymentPlanDto> list(Pageable page) {
        return service.list(page);
    }

    @GetMapping("/student/{studentId}")
    public List<PaymentPlanDto> listByStudent(@PathVariable UUID studentId) {
        return service.listByStudent(studentId);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('FINANCIAL')")
    public PaymentPlanDto update(@PathVariable UUID id,
            @Valid @RequestBody PaymentPlanUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('FINANCIAL')")
    public void cancel(@PathVariable UUID id) {
        service.cancel(id);
    }
}
