package com.school.services;

import com.school.controllers.dto.guardian.GuardianCreateDto;
import com.school.controllers.dto.guardian.GuardianDto;
import com.school.controllers.dto.guardian.GuardianUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GuardianService {
    GuardianDto create(GuardianCreateDto dto);

    GuardianDto update(UUID id, GuardianUpdateDto dto);

    GuardianDto findById(UUID id);

    Page<GuardianDto> findAll(Pageable pageable);

    void delete(UUID id);
}
