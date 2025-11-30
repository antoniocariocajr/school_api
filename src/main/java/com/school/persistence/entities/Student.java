package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "student")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    private Person person;

    @Column(name = "enrollment_number", unique = true, nullable = false)
    private String enrollmentNumber;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    private LocalDate exitDate;

    private String originSchool; // escola de onde veio

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "financial_guardian_id")
    private Guardian financialGuardian;

    @ManyToMany
    @JoinTable(name = "student_legal_guardian",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "guardian_id"))
    private Set<Guardian> legalGuardians = new HashSet<>();

    private Boolean active = true;
}