package com.school.services;

import com.school.controllers.dto.employee.EmployeeCreateDto;
import com.school.controllers.dto.employee.EmployeeDto;
import com.school.controllers.dto.employee.EmployeeUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmployeeService {
    EmployeeDto create(EmployeeCreateDto dto);

    EmployeeDto update(UUID id, EmployeeUpdateDto dto);

    EmployeeDto findById(UUID id);

    Page<EmployeeDto> findAll(Pageable pageable);

    void delete(UUID id);
}
