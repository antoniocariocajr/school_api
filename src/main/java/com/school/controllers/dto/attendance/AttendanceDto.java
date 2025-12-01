package com.school.controllers.dto.attendance;

import com.school.controllers.dto.enrollment.EnrollmentDto;
import com.school.controllers.dto.schedule.ScheduleDto;
import com.school.persistence.entities.Attendance.Status;
import java.time.LocalDate;
import java.util.UUID;

public record AttendanceDto(
        UUID id,
        EnrollmentDto enrollment,
        ScheduleDto schedule,
        LocalDate classDate,
        Status status,
        String createdBy) {
}
