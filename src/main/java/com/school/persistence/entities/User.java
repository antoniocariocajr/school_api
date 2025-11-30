package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users") // “user” é palavra reservada em vários bancos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private Person person;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private Role role;

    private boolean active = true;

    @Column(name = "last_access")
    private Instant lastAccess;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    /* helper – não expõe a senha nos DTOs */
    public void updateLastAccess() {
        this.lastAccess = Instant.now();
    }
    @Getter @AllArgsConstructor
    public enum Role {
        ADMIN(1L),
        TEACHER(2L),
        STUDENT(3L),
        PARENT(4L),
        EMPLOYEE(5L),
        GUARDIAN(6L);
        private final long id;
    }
}


