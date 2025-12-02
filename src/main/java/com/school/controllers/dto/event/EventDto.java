package com.school.controllers.dto.event;

import com.school.controllers.dto.schoolterm.SchoolTermDto;
import com.school.persistence.entities.Event.TypeEvent;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(
        UUID id,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        TypeEvent typeEvent,
        SchoolTermDto schoolTerm,
        Boolean active) {
}
