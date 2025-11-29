package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "report_card")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReportCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", unique = true, nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "school_term_id", nullable = false)
    private SchoolTerm schoolTerm;

    @Column(name = "final_grade", precision = 5, scale = 2)
    private BigDecimal finalGrade;

    @Column(name = "final_attendance", precision = 5, scale = 2)
    private BigDecimal finalAttendance; // percentual 0-100

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status = Status.IN_PROGRESS;

    public enum Status { IN_PROGRESS, APPROVED, RECOVERY, FAILED }

    @Column(columnDefinition = "TEXT")
    private String remarks; // observações do professor / coordenador
}
