package com.school.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.school.controllers.dto.schedule.ScheduleCreateDto;
import com.school.controllers.dto.schedule.ScheduleDto;
import com.school.controllers.dto.schedule.ScheduleUpdateDto;
import com.school.persistence.entities.Schedule;
import com.school.persistence.entities.SchoolClass;
import com.school.persistence.repositories.ScheduleRepository;
import com.school.persistence.repositories.SchoolClassRepository;
import com.school.services.ScheduleService;
import com.school.services.mapper.ScheduleMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public Page<ScheduleDto> findAll(Pageable page) {
        return scheduleRepository.findAll(page).map(scheduleMapper::toDto);
    }

    @Override
    public ScheduleDto findById(UUID id) {
        return scheduleMapper.toDto(
                scheduleRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Schedule not found")));
    }

    @Override
    public ScheduleDto create(ScheduleCreateDto dto) {
        SchoolClass schoolClass = schoolClassRepository.findById(dto.schoolClassId())
                .orElseThrow(() -> new RuntimeException("School class not found"));
        return scheduleMapper.toDto(scheduleRepository.save(scheduleMapper.toEntity(schoolClass, dto)));
    }

    @Override
    public ScheduleDto update(UUID id, ScheduleUpdateDto dto) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        scheduleMapper.updateEntity(schedule, dto);
        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    @Override
    public void delete(UUID id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        scheduleRepository.delete(schedule);
    }
}
