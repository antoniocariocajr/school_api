package com.school.services.mapper;

import org.springframework.stereotype.Component;

import com.school.controllers.dto.document.DocumentCreateDto;
import com.school.controllers.dto.document.DocumentDto;
import com.school.persistence.entities.Document;
import com.school.persistence.entities.Person;

@Component
public class DocumentMapper {

    public DocumentDto toDto(Document d) {
        return new DocumentDto(
                d.getId(),
                d.getOriginalName(),
                d.getContentType(),
                d.getSize(),
                d.getStorageKey(),
                d.getType(),
                d.getUploadedAt(),
                d.getUploadedBy());
    }

    public Document toEntity(DocumentCreateDto dto, Person person, String storageKey, String uploadedBy) {
        return Document.builder()
                .person(person)
                .originalName(dto.file().getOriginalFilename())
                .contentType(dto.file().getContentType())
                .size(dto.file().getSize())
                .storageKey(storageKey)
                .type(dto.type())
                .uploadedBy(uploadedBy)
                .build();
    }
}
