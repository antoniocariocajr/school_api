package com.school.controllers.dto.attendance;

import com.school.persistence.entities.Attendance.Status;
import java.time.LocalDate;

public record AttendanceUpdateDto(
        LocalDate classDate,
        Status status,
        String createdBy) {
}
