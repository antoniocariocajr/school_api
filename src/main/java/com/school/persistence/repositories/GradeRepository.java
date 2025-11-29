package com.school.persistence.repositories;

import com.school.persistence.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, UUID> {
    List<Grade> findByEnrollmentId(UUID enrollmentId);
}
