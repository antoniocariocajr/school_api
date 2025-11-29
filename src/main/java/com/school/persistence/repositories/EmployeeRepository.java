package com.school.persistence.repositories;


import com.school.persistence.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository  extends JpaRepository<Employee, UUID> { }
