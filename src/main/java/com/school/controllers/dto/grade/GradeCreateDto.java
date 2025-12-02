package com.school.controllers.dto.grade;

import com.school.persistence.entities.Grade.TypeGrade;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record GradeCreateDto(
        @NotNull UUID enrollmentId,
        @NotNull TypeGrade typeGrade,
        @NotNull BigDecimal value,
        BigDecimal weight,
        @NotNull Date evaluationDate,
        String description) {
}
