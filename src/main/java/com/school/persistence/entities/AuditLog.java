package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_email", nullable = false, length = 120)
    private String userEmail;      // quem fez

    @Column(name = "action", nullable = false, length = 50)
    private String action;         // CREATE, UPDATE, DELETE, LOGIN...

    @Column(name = "entity_name", nullable = false, length = 60)
    private String entityName;     // Student, Grade, User...

    @Column(name = "entity_id", length = 36)
    private String entityId;       // UUID da entidade afetada

    @Column(name = "field", length = 60)
    private String field;          // campo alterado (opcional)

    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;       // valor antes

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;       // valor depois

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private Instant timestamp;
}
