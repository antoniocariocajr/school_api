package com.school.persistence.repositories;

import com.school.persistence.entities.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByPersonId(UUID personId);

    Optional<Student> findByPersonId(UUID personId);
}
