package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "school_class")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "school_term_id", nullable = false)
    private SchoolTerm schoolTerm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id") // pode ser null até designarem professor
    private Teacher teacher;

    @Column(name = "class_code", unique = true, nullable = false, length = 30)
    private String code; // 2025-1-MAT-I-A

    @Column(name = "max_students")
    private Integer maxStudents = 50;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Status status = Status.ACTIVE;

    public enum Status { ACTIVE, CANCELED, COMPLETED }
}
