package com.school.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.school.controllers.dto.schedule.ScheduleCreateDto;
import com.school.controllers.dto.schedule.ScheduleDto;
import com.school.controllers.dto.schedule.ScheduleUpdateDto;

public interface ScheduleService {
    Page<ScheduleDto> findAll(Pageable page);

    ScheduleDto findById(UUID id);

    ScheduleDto create(ScheduleCreateDto dto);

    ScheduleDto update(UUID id, ScheduleUpdateDto dto);

    void delete(UUID id);
}
