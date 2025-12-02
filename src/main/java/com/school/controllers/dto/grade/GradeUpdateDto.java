package com.school.controllers.dto.grade;

import com.school.persistence.entities.Grade.TypeGrade;
import java.math.BigDecimal;
import java.util.Date;

public record GradeUpdateDto(
        TypeGrade typeGrade,
        BigDecimal value,
        BigDecimal weight,
        Date evaluationDate,
        String description) {
}
