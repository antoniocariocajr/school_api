package com.school.persistence.repositories;

import com.school.persistence.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    List<Schedule> findBySchoolClassId(UUID schoolClassId);
    List<Schedule> findByRoomsId(UUID roomId);
}
