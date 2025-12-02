package com.school.services.mapper;

import com.school.controllers.dto.event.EventCreateDto;
import com.school.controllers.dto.event.EventDto;
import com.school.controllers.dto.event.EventUpdateDto;
import com.school.persistence.entities.Event;
import com.school.persistence.entities.SchoolTerm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final SchoolTermMapper schoolTermMapper;

    public EventDto toDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartDate(),
                event.getEndDate(),
                event.getTypeEvent(),
                event.getSchoolTerm() != null ? schoolTermMapper.toDto(event.getSchoolTerm()) : null,
                event.getActive());
    }

    public Event toEntity(SchoolTerm schoolTerm, EventCreateDto dto) {
        return Event.builder()
                .title(dto.title())
                .description(dto.description())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .typeEvent(dto.typeEvent())
                .schoolTerm(schoolTerm)
                .active(true)
                .build();
    }

    public void updateEntity(Event event, SchoolTerm schoolTerm, EventUpdateDto dto) {
        if (dto.title() != null) {
            event.setTitle(dto.title());
        }
        if (dto.description() != null) {
            event.setDescription(dto.description());
        }
        if (dto.startDate() != null) {
            event.setStartDate(dto.startDate());
        }
        if (dto.endDate() != null) {
            event.setEndDate(dto.endDate());
        }
        if (dto.typeEvent() != null) {
            event.setTypeEvent(dto.typeEvent());
        }
        if (dto.schoolTermId() != null) {
            event.setSchoolTerm(schoolTerm);
        }
        if (dto.active() != null) {
            event.setActive(dto.active());
        }
    }
}
