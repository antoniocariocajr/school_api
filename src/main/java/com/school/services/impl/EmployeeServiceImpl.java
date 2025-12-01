package com.school.services.impl;

import com.school.controllers.dto.employee.EmployeeCreateDto;
import com.school.controllers.dto.employee.EmployeeDto;
import com.school.controllers.dto.employee.EmployeeUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Employee;
import com.school.persistence.entities.Person;
import com.school.persistence.repositories.EmployeeRepository;
import com.school.persistence.repositories.PersonRepository;
import com.school.services.EmployeeService;
import com.school.services.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeDto create(EmployeeCreateDto dto) {
        Person person = personRepository.findById(dto.personId())
                .orElseThrow(() -> new BusinessException("Person not found with ID: " + dto.personId()));

        if (employeeRepository.existsByPersonId(dto.personId())) {
            throw new BusinessException("This person is already an employee.");
        }

        Employee employee = employeeMapper.toEntity(person, dto);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto update(UUID id, EmployeeUpdateDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Employee not found with ID: " + id));

        employeeMapper.updateEntity(employee, dto);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto findById(UUID id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new BusinessException("Employee not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDto> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Employee not found with ID: " + id));
        employee.setActive(false); // Soft delete
        employeeRepository.save(employee);
    }
}
