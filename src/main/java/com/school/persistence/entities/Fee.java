package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fee")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_plan_id", nullable = false)
    private PaymentPlan paymentPlan;

    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "original_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal originalValue;

    @Column(name = "fine", precision = 10, scale = 2)
    private BigDecimal fine = BigDecimal.ZERO;

    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Status status = Status.PENDING;

    public enum Status { PENDING, PAID, OVERDUE, WAIVED }

    /* valor final = original + fine */
    public BigDecimal getTotalValue() {
        return originalValue.add(fine);
    }

    /* marca como pago */
    public void pay(LocalDate when) {
        this.paidDate = when;
        this.status = Status.PAID;
    }
}
