package com.school.controllers.dto.grade;

import com.school.controllers.dto.enrollment.EnrollmentDto;
import com.school.persistence.entities.Grade.TypeGrade;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record GradeDto(
        UUID id,
        EnrollmentDto enrollment,
        TypeGrade typeGrade,
        BigDecimal value,
        BigDecimal weight,
        Date evaluationDate,
        String description) {
}
