package com.school.controllers.dto.employee;

import com.school.persistence.entities.Employee.Role;
import java.time.LocalDate;

public record EmployeeUpdateDto(
        String registrationCode,
        LocalDate hireDate,
        LocalDate leaveDate,
        Role role,
        Boolean active) {
}
