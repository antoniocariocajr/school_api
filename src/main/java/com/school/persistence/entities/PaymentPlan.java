package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "payment_plan")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "school_term_id", nullable = false)
    private SchoolTerm schoolTerm;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalValue;

    @Column(precision = 5, scale = 2)
    private BigDecimal discount; // valor absoluto

    @Column(name = "installment_count", nullable = false)
    private Integer installmentCount; // 1 para pagamento único, 10 para mensal

    @Column(name = "due_day", nullable = false)
    private Integer dueDay; // dia do mês (1-31)

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Status status = Status.ACTIVE;

    public enum Status { ACTIVE, CANCELED, PAID }

    @OneToMany(mappedBy = "paymentPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Fee> fees = new ArrayList<>();

    /* helper – gera parcelas após persistir */
    public void generateFees() {
        fees.clear();
        BigDecimal base = totalValue.subtract(discount);
        BigDecimal installmentValue = base.divide(BigDecimal.valueOf(installmentCount), 2, BigDecimal.ROUND_HALF_EVEN);

        LocalDate start = schoolTerm.getStartDate().withDayOfMonth(dueDay);
        for (int i = 0; i < installmentCount; i++) {
            LocalDate due = start.plusMonths(i);
            fees.add(Fee.builder()
                    .paymentPlan(this)
                    .installmentNumber(i + 1)
                    .dueDate(due)
                    .originalValue(installmentValue)
                    .build());
        }
    }
}
