package com.school.controllers.dto.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import com.school.controllers.dto.schoolclass.SchoolClassDto;

public record ScheduleDto(
        UUID id,
        SchoolClassDto schoolClass,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        boolean active) {

}
