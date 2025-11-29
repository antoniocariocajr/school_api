package com.school.persistence.repositories;

import com.school.persistence.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    List<Attendance> findByScheduleIdAndClassDate(UUID scheduleId, LocalDate date);
    List<Attendance> findByEnrollmentId(UUID enrollmentId);
    Optional<Attendance> findByEnrollmentIdAndScheduleIdAndClassDate(
            UUID enrollmentId, UUID scheduleId, LocalDate date);
}
