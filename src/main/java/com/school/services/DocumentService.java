package com.school.services;

import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;

import com.school.controllers.dto.document.DocumentCreateDto;
import com.school.controllers.dto.document.DocumentDto;
import com.school.persistence.entities.Document;

public interface DocumentService {
    DocumentDto upload(UUID personId, DocumentCreateDto dto, String uploadedBy);

    List<DocumentDto> listByPerson(UUID personId);

    List<DocumentDto> listByPersonAndType(UUID personId, Document.Type type);

    Resource download(UUID documentId);

    void delete(UUID documentId);
}
