package com.school.controllers.dto.attendance;

import com.school.persistence.entities.Attendance.Status;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record AttendanceCreateDto(
        @NotNull UUID enrollmentId,
        @NotNull UUID scheduleId,
        @NotNull LocalDate classDate,
        @NotNull Status status,
        String createdBy) {
}
