package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "registration_code", unique = true, nullable = false)
    private String registrationCode; // código interno da escola

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    private LocalDate leaveDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private Role role;

    private Boolean active = true;

    public enum Role { SECRETARY, COORDINATOR, PRINCIPAL, FINANCIAL, SUPPORT }

}
