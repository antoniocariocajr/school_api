package com.school.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.controllers.dto.fee.FeeDto;
import com.school.controllers.dto.fee.FeePayDto;
import com.school.services.FeeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
@Tag(name = "Fees", description = "Parcelas de planos de pagamento")
public class FeeController {

    private final FeeService service;

    @GetMapping("/plan/{paymentPlanId}")
    public List<FeeDto> listByPlan(@PathVariable UUID paymentPlanId) {
        return service.listByPlan(paymentPlanId);
    }

    @PatchMapping("/{id}/pay")
    @PreAuthorize("hasRole('FINANCIAL')")
    public FeeDto pay(@PathVariable UUID id,
            @Valid @RequestBody FeePayDto dto) {
        return service.payInstallment(id, dto);
    }

    @PostMapping("/overdue-fines")
    @PreAuthorize("hasRole('FINANCIAL')")
    public ResponseEntity<?> generateFines() {
        service.generateOverdueFines();
        return ResponseEntity.ok().build();
    }
}
