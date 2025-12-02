package com.school.services.impl;

import com.school.controllers.dto.event.EventCreateDto;
import com.school.controllers.dto.event.EventDto;
import com.school.controllers.dto.event.EventUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Event;
import com.school.persistence.entities.SchoolTerm;
import com.school.persistence.repositories.EventRepository;
import com.school.persistence.repositories.SchoolTermRepository;
import com.school.services.EventService;
import com.school.services.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final SchoolTermRepository schoolTermRepository;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventDto create(EventCreateDto dto) {
        SchoolTerm schoolTerm = null;
        if (dto.schoolTermId() != null) {
            schoolTerm = schoolTermRepository.findById(dto.schoolTermId())
                    .orElseThrow(() -> new BusinessException("School Term not found with ID: " + dto.schoolTermId()));
        }

        Event event = eventMapper.toEntity(schoolTerm, dto);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto update(UUID id, EventUpdateDto dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Event not found with ID: " + id));

        SchoolTerm schoolTerm = event.getSchoolTerm();
        if (dto.schoolTermId() != null) {
            schoolTerm = schoolTermRepository.findById(dto.schoolTermId())
                    .orElseThrow(() -> new BusinessException("School Term not found with ID: " + dto.schoolTermId()));
        }

        eventMapper.updateEntity(event, schoolTerm, dto);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public EventDto findById(UUID id) {
        return eventRepository.findById(id)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new BusinessException("Event not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventDto> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable).map(eventMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Event not found with ID: " + id));

        event.setActive(false);
        eventRepository.save(event);
    }
}
