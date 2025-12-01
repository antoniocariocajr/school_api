package com.school.services.mapper;

import com.school.controllers.dto.attendance.AttendanceCreateDto;
import com.school.controllers.dto.attendance.AttendanceDto;
import com.school.controllers.dto.attendance.AttendanceUpdateDto;
import com.school.persistence.entities.Attendance;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceMapper {

    private final EnrollmentMapper enrollmentMapper;
    private final ScheduleMapper scheduleMapper;

    public AttendanceDto toDto(Attendance attendance) {
        return new AttendanceDto(
                attendance.getId(),
                enrollmentMapper.toDto(attendance.getEnrollment()),
                scheduleMapper.toDto(attendance.getSchedule()),
                attendance.getClassDate(),
                attendance.getStatus(),
                attendance.getCreatedBy());
    }

    public Attendance toEntity(Enrollment enrollment, Schedule schedule, AttendanceCreateDto dto) {
        return Attendance.builder()
                .enrollment(enrollment)
                .schedule(schedule)
                .classDate(dto.classDate())
                .status(dto.status())
                .createdBy(dto.createdBy())
                .build();
    }

    public void updateEntity(Attendance attendance, AttendanceUpdateDto dto) {
        if (dto.classDate() != null) {
            attendance.setClassDate(dto.classDate());
        }
        if (dto.status() != null) {
            attendance.setStatus(dto.status());
        }
        if (dto.createdBy() != null) {
            attendance.setCreatedBy(dto.createdBy());
        }
    }
}
