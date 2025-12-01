package com.school.controllers.dto.enrollment;

import com.school.controllers.dto.student.StudentDto;
import com.school.persistence.entities.Enrollment.Status;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EnrollmentDto(
        UUID id,
        StudentDto student,
        UUID schoolClassId, // Returning ID to avoid circular dependency or too deep nesting, can be
                            // expanded if needed
        LocalDate enrollmentDate,
        Status status,
        BigDecimal finalGrade,
        BigDecimal finalAttendance) {
}
