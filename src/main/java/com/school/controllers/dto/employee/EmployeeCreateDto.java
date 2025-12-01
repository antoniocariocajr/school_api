package com.school.controllers.dto.employee;

import com.school.persistence.entities.Employee.Role;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeCreateDto(
        @NotNull UUID personId,
        @NotNull String registrationCode,
        @NotNull LocalDate hireDate,
        @NotNull Role role) {
}
