package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "grade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TypeGrade typeGrade; // P1, P2, TRABALHO, RECUPERACAO

    public enum TypeGrade {
        P1, P2, TRABALHO, RECUPERACAO
    }

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal value;

    @Builder.Default
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal weight = BigDecimal.ONE;

    @Column(name = "evaluation_date", nullable = false)
    private Date evaluationDate;

    @Column(length = 255)
    private String description;
}
