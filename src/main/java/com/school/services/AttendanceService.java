package com.school.services;

import com.school.controllers.dto.attendance.AttendanceCreateDto;
import com.school.controllers.dto.attendance.AttendanceDto;
import com.school.controllers.dto.attendance.AttendanceUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AttendanceService {
    AttendanceDto create(AttendanceCreateDto dto);

    AttendanceDto update(UUID id, AttendanceUpdateDto dto);

    AttendanceDto findById(UUID id);

    Page<AttendanceDto> findAll(Pageable pageable);

    void delete(UUID id);
}
