package com.school.controllers.dto.schoolterm;

import java.time.LocalDate;

public record SchoolTermCreateDto(String code, LocalDate startDate, LocalDate endDate) {

}
