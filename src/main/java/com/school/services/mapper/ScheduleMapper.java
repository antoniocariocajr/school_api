package com.school.services.mapper;

import org.springframework.stereotype.Component;

import com.school.controllers.dto.schedule.ScheduleCreateDto;
import com.school.controllers.dto.schedule.ScheduleDto;
import com.school.controllers.dto.schedule.ScheduleUpdateDto;
import com.school.persistence.entities.Schedule;
import com.school.persistence.entities.SchoolClass;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduleMapper {
    private final SchoolClassMapper schoolClassMapper;

    public ScheduleDto toDto(Schedule schedule) {
        return new ScheduleDto(
                schedule.getId(),
                schoolClassMapper.toDto(schedule.getSchoolClass()),
                schedule.getDayOfWeek(),
                schedule.getStartTime(),
                schedule.getEndTime(),
                schedule.getActive());
    }

    public Schedule toEntity(SchoolClass schoolClass, ScheduleCreateDto dto) {
        return Schedule.builder()
                .schoolClass(schoolClass)
                .dayOfWeek(dto.dayOfWeek())
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .build();
    }

    public void updateEntity(Schedule schedule, ScheduleUpdateDto dto) {
        if (dto.dayOfWeek() != null) {
            schedule.setDayOfWeek(dto.dayOfWeek());
        }
        if (dto.startTime() != null) {
            schedule.setStartTime(dto.startTime());
        }
        if (dto.endTime() != null) {
            schedule.setEndTime(dto.endTime());
        }
        schedule.setActive(dto.active());
    }

}
