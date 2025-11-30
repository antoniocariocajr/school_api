package com.school.persistence.repositories;

import com.school.persistence.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    List<AuditLog> findByEntityNameAndEntityIdOrderByTimestampDesc(String entity, String id);
    List<AuditLog> findByUserEmailOrderByTimestampDesc(String email);
}
