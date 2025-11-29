package com.school.persistence.repositories;

import com.school.persistence.entities.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FeeRepository extends JpaRepository<Fee, UUID> {
    List<Fee> findByPaymentPlanId(UUID paymentPlanId);
    List<Fee> findByStatus(Fee.Status status);
    List<Fee> findByDueDateBeforeAndStatus(LocalDate date, Fee.Status status);
}
