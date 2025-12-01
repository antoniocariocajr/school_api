package com.school.controllers.dto.teacher;

import java.time.LocalDate;

public record TeacherUpdateDto(
        String registrationCode,
        LocalDate hireDate,
        LocalDate leaveDate,
        Boolean active) {
}
