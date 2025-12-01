package com.school.controllers.dto.employee;

import com.school.controllers.dto.person.PersonDto;
import com.school.persistence.entities.Employee.Role;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeDto(
        UUID id,
        PersonDto person,
        String registrationCode,
        LocalDate hireDate,
        LocalDate leaveDate,
        Role role,
        Boolean active) {
}
