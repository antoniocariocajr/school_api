package com.school.controllers.dto.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleUpdateDto(
                DayOfWeek dayOfWeek,
                LocalTime startTime,
                LocalTime endTime,
                boolean active) {

}
