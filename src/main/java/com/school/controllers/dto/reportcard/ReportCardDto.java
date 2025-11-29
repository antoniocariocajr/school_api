package com.school.controllers.dto.reportcard;

import com.school.persistence.entities.ReportCard;

import java.math.BigDecimal;
import java.util.UUID;

public record ReportCardDto(UUID id,
                            BigDecimal finalGrade,
                            BigDecimal finalAttendance,
                            ReportCard.Status status,
                            String remarks) {
    public static ReportCardDto from(ReportCard r) {
        return new ReportCardDto(r.getId(), r.getFinalGrade(), r.getFinalAttendance(), r.getStatus(), r.getRemarks());
    }
}


