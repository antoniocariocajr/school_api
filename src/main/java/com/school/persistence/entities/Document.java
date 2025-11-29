package com.school.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "document")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(nullable = false, length = 120)
    private String originalName;

    @Column(nullable = false, length = 30)
    private String contentType; // application/pdf, image/jpeg

    @Column(nullable = false)
    private Long size; // bytes

    @Column(nullable = false, unique = true, length = 150)
    private String storageKey; // path S3 ou disco: 2025/11/UUID_declaracao.pdf

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private Type type;

    public enum Type {
        MEDICAL_CERTIFICATE, TRANSFER, ENROLLMENT_DECLARATION,
        GRADE_HISTORY, PHOTO, OTHER
    }

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "uploaded_by", length = 120, nullable = false)
    private String uploadedBy; // email do usuário que fez upload
}
