package com.school.services.mapper;

import com.school.controllers.dto.employee.EmployeeCreateDto;
import com.school.controllers.dto.employee.EmployeeDto;
import com.school.controllers.dto.employee.EmployeeUpdateDto;
import com.school.controllers.dto.person.PersonDto;
import com.school.persistence.entities.Employee;
import com.school.persistence.entities.Person;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto toDto(Employee employee) {
        PersonDto personDto = PersonDto.toDto(employee.getPerson());
        return new EmployeeDto(
                employee.getId(),
                personDto,
                employee.getRegistrationCode(),
                employee.getHireDate(),
                employee.getLeaveDate(),
                employee.getRole(),
                employee.getActive());
    }

    public Employee toEntity(Person person, EmployeeCreateDto dto) {
        return Employee.builder()
                .person(person)
                .registrationCode(dto.registrationCode())
                .hireDate(dto.hireDate())
                .role(dto.role())
                .active(true)
                .build();
    }

    public void updateEntity(Employee employee, EmployeeUpdateDto dto) {
        if (dto.registrationCode() != null) {
            employee.setRegistrationCode(dto.registrationCode());
        }
        if (dto.hireDate() != null) {
            employee.setHireDate(dto.hireDate());
        }
        if (dto.leaveDate() != null) {
            employee.setLeaveDate(dto.leaveDate());
        }
        if (dto.role() != null) {
            employee.setRole(dto.role());
        }
        if (dto.active() != null) {
            employee.setActive(dto.active());
        }
    }
}
