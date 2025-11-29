package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "person_address")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class PersonAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_address", length = 15, nullable = false)
    private TypeAddress typeAddress;

    @Column(name = "is_current", nullable = false)
    private boolean current = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /* garante só 1 corrente por tipo (via BD) */
    public static final String UNIQUE_CURRENT =
            "CREATE UNIQUE INDEX IF NOT EXISTS ux_person_address_current " +
                    "ON person_address (person_id, type) " +
                    "WHERE is_current = true";

    @AllArgsConstructor @Getter
    public enum TypeAddress {
        HOME(1L),
        WORK(2L),
        BILLING(3L),
        PARENT(4L),
        TEMPORARY(5L);
        private final Long id;
    }

}

