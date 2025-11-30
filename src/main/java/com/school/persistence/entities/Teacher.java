package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "teacher")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", unique = true, nullable = false)
    private Person person;

    @Column(name = "registration_code", unique = true)
    private String registrationCode; // CREA, CRM, etc.

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    private LocalDate leaveDate;

    private Boolean active = true;
}
