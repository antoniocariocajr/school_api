package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "school_class_id", nullable = false)
    private SchoolClass schoolClass;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Status status = Status.ACTIVE;

    /* campos calculados (podem ser atualizados por JOB ou triggers) */
    @Column(name = "final_grade", precision = 5, scale = 2)
    private BigDecimal finalGrade;

    @Column(name = "final_attendance", precision = 5, scale = 2)
    private BigDecimal finalAttendance; // percentual 0-100

    public enum Status {
        ACTIVE, LOCKED, CANCELED, COMPLETED
    }

}
