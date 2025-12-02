package com.school.services;

import com.school.controllers.dto.event.EventCreateDto;
import com.school.controllers.dto.event.EventDto;
import com.school.controllers.dto.event.EventUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {
    EventDto create(EventCreateDto dto);

    EventDto update(UUID id, EventUpdateDto dto);

    EventDto findById(UUID id);

    Page<EventDto> findAll(Pageable pageable);

    void delete(UUID id);
}
