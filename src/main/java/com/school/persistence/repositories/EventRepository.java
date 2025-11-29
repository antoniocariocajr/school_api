package com.school.persistence.repositories;

import com.school.persistence.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository  extends JpaRepository<Event, UUID> {
    List<Event> findByStartDateBetween(LocalDateTime start, LocalDateTime end);
}
