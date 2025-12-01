package com.school.persistence.repositories;

import com.school.persistence.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    Optional<Enrollment> findByStudentIdAndSchoolClassId(UUID studentId, UUID schoolClassId);

    List<Enrollment> findByStudentId(UUID studentId);

    List<Enrollment> findBySchoolClassId(UUID schoolClassId);

    boolean existsByStudentIdAndSchoolClassId(UUID studentId, UUID schoolClassId);

    @Query("""
                SELECT e
                FROM Enrollment e
                WHERE e.schoolClass.schoolTerm.id = :schoolTermId
            """)
    List<Enrollment> findBySchoolTermId(@Param("schoolTermId") UUID schoolTermId);
}
