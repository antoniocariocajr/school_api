package com.school.controllers.dto.document;

import com.school.persistence.entities.Document;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentDto(UUID id, String originalName, Document.Type type, LocalDateTime uploadedAt) {
    public static DocumentDto from(Document d) {
        return new DocumentDto(d.getId(), d.getOriginalName(), d.getType(), d.getUploadedAt());
    }
}
