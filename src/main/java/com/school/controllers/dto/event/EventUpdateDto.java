package com.school.controllers.dto.event;

import com.school.persistence.entities.Event.TypeEvent;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventUpdateDto(
        @Size(max = 120) String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        TypeEvent typeEvent,
        UUID schoolTermId,
        Boolean active) {
}
