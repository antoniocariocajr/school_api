package com.school.controllers.dto.schoolterm;

import java.time.LocalDate;

public record SchoolTermUpdateDto(String code, LocalDate startDate, LocalDate endDate, Boolean active) {

}
