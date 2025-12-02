package com.school.controllers.dto.reportcard;

import com.school.persistence.entities.ReportCard;

import java.math.BigDecimal;
import java.util.UUID;

public record ReportCardDto(
        UUID id,
        UUID enrollmentId,
        String studentName,
        String disciplineName,
        String schoolTermCode,
        BigDecimal finalGrade,
        BigDecimal finalAttendance,
        ReportCard.Status status,
        String remarks) {
}
