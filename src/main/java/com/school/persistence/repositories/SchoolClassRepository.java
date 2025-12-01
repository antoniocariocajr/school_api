package com.school.persistence.repositories;

import com.school.persistence.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, UUID> {
    List<SchoolClass> findByDisciplineId(UUID disciplineId);

    List<SchoolClass> findByTeacherId(UUID teacherId);

    List<SchoolClass> findBySchoolTermId(UUID schoolTermId);

    boolean existsByCode(String code);
}
