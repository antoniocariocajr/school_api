package com.school.services.impl;

import com.school.persistence.entities.AuditLog;
import com.school.persistence.repositories.AuditLogRepository;
import com.school.services.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final HttpServletRequest request; // injetado pelo Spring

    @Override
    public void log(String action, String entityName, String entityId,
                    String field, String oldValue, String newValue) {

        String email = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getName)
                .orElse("system");

        AuditLog entry = AuditLog.builder()
                .userEmail(email)
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .field(field)
                .oldValue(oldValue)
                .newValue(newValue)
                .ipAddress(getClientIp())
                .userAgent(request.getHeader("User-Agent"))
                .build();
        auditLogRepository.save(entry);
    }

    private String getClientIp() {
        String xf = request.getHeader("X-Forwarded-For");
        return (xf != null) ? xf.split(",")[0].trim() : request.getRemoteAddr();
    }
}
