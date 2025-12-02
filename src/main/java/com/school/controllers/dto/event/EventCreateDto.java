package com.school.controllers.dto.event;

import com.school.persistence.entities.Event.TypeEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventCreateDto(
        @NotBlank @Size(max = 120) String title,
        String description,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @NotNull TypeEvent typeEvent,
        UUID schoolTermId) {
}
