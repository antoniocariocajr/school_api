package com.school.persistence.repositories;

import com.school.persistence.entities.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, UUID> {
    Optional<PaymentPlan> findByStudentIdAndSchoolTermIdAndStatus(
            UUID studentId, UUID schoolTermId, PaymentPlan.Status status);

    List<PaymentPlan> findByStudentId(UUID studentId);

    List<PaymentPlan> findByStudentIdAndSchoolTermId(UUID studentId, UUID schoolTermId);
}
