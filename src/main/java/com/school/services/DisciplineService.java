package com.school.services;

import com.school.controllers.dto.discipline.DisciplineCreateDto;
import com.school.controllers.dto.discipline.DisciplineDto;
import com.school.controllers.dto.discipline.DisciplineUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DisciplineService {
    DisciplineDto create(DisciplineCreateDto dto);

    DisciplineDto update(UUID id, DisciplineUpdateDto dto);

    DisciplineDto findById(UUID id);

    Page<DisciplineDto> findAll(Pageable pageable);

    void delete(UUID id);
}
