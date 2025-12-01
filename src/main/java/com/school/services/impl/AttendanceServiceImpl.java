package com.school.services.impl;

import com.school.controllers.dto.attendance.AttendanceCreateDto;
import com.school.controllers.dto.attendance.AttendanceDto;
import com.school.controllers.dto.attendance.AttendanceUpdateDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Attendance;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.Schedule;
import com.school.persistence.repositories.AttendanceRepository;
import com.school.persistence.repositories.EnrollmentRepository;
import com.school.persistence.repositories.ScheduleRepository;
import com.school.services.AttendanceService;
import com.school.services.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    @Transactional
    public AttendanceDto create(AttendanceCreateDto dto) {
        Enrollment enrollment = enrollmentRepository.findById(dto.enrollmentId())
                .orElseThrow(() -> new BusinessException("Enrollment not found with ID: " + dto.enrollmentId()));

        Schedule schedule = scheduleRepository.findById(dto.scheduleId())
                .orElseThrow(() -> new BusinessException("Schedule not found with ID: " + dto.scheduleId()));

        Attendance attendance = attendanceMapper.toEntity(enrollment, schedule, dto);
        attendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDto(attendance);
    }

    @Override
    @Transactional
    public AttendanceDto update(UUID id, AttendanceUpdateDto dto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Attendance not found with ID: " + id));

        attendanceMapper.updateEntity(attendance, dto);
        attendance = attendanceRepository.save(attendance);
        return attendanceMapper.toDto(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceDto findById(UUID id) {
        return attendanceRepository.findById(id)
                .map(attendanceMapper::toDto)
                .orElseThrow(() -> new BusinessException("Attendance not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttendanceDto> findAll(Pageable pageable) {
        return attendanceRepository.findAll(pageable).map(attendanceMapper::toDto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!attendanceRepository.existsById(id)) {
            throw new BusinessException("Attendance not found with ID: " + id);
        }
        attendanceRepository.deleteById(id);
    }
}
