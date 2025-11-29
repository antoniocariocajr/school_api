package com.school.persistence.repositories;

import com.school.persistence.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findByPersonId(UUID personId);
    List<Document> findByPersonIdAndType(UUID personId, Document.Type type);
}
