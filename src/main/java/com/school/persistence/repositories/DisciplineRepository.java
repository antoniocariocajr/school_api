package com.school.persistence.repositories;

import com.school.persistence.entities.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {
    List<Discipline> findByCourseId(UUID courseId);
}
