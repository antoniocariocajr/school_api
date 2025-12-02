package com.school.controllers.dto.document;

import com.school.persistence.entities.Document;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentDto(
                UUID id,
                String originalName,
                String contentType,
                Long size,
                String storageKey,
                Document.Type type,
                LocalDateTime uploadedAt,
                String uploadedBy) {
}
