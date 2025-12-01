package com.school.controllers.dto.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleCreateDto(
                UUID schoolClassId,
                DayOfWeek dayOfWeek,
                LocalTime startTime,
                LocalTime endTime) {

}
