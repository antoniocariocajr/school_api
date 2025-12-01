package com.school.controllers.dto.student;

import java.time.LocalDate;

public record StudentUpdateDto(
        String enrollmentNumber,
        LocalDate entryDate,
        LocalDate exitDate,
        String originSchool,
        Boolean active) {
}
