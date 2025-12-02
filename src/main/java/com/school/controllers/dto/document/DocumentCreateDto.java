package com.school.controllers.dto.document;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import com.school.persistence.entities.Document;

public record DocumentCreateDto(
        @NotNull MultipartFile file,
        @NotNull Document.Type type) {
}
