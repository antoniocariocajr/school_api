package com.school.controllers.dto.enrollment;

import com.school.persistence.entities.Enrollment.Status;
import java.math.BigDecimal;

public record EnrollmentUpdateDto(
        Status status,
        BigDecimal finalGrade,
        BigDecimal finalAttendance) {
}
