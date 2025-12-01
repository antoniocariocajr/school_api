package com.school.services;

import com.school.controllers.dto.schoolclass.SchoolClassCreateDto;
import com.school.controllers.dto.schoolclass.SchoolClassDto;
import com.school.controllers.dto.schoolclass.SchoolClassUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SchoolClassService {
    SchoolClassDto create(SchoolClassCreateDto dto);

    SchoolClassDto update(UUID id, SchoolClassUpdateDto dto);

    SchoolClassDto findById(UUID id);

    Page<SchoolClassDto> findAll(Pageable pageable);

    void delete(UUID id);
}
