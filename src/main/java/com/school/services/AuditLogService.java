package com.school.services;

public interface AuditLogService {
    void log(String action, String entityName, String entityId,
             String field, String oldValue, String newValue);
}
