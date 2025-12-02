package com.school.persistence.repositories;

import com.school.persistence.entities.ReportCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportCardRepository extends JpaRepository<ReportCard, UUID> {
    Optional<ReportCard> findByEnrollmentIdAndSchoolTermId(UUID enrollmentId, UUID schoolTermId);

    List<ReportCard> findBySchoolTermId(UUID schoolTermId);

    List<ReportCard> findByEnrollmentStudentId(UUID studentId);

    List<ReportCard> findByEnrollmentSchoolClassId(UUID schoolClassId);
}
