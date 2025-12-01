package com.school.controllers.dto.schoolterm;

import java.time.LocalDate;
import java.util.UUID;

public record SchoolTermDto(UUID id, String code, LocalDate startDate, LocalDate endDate, Boolean active) {

}
