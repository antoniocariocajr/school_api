package com.school.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.school.controllers.dto.schoolterm.SchoolTermCreateDto;
import com.school.controllers.dto.schoolterm.SchoolTermDto;
import com.school.controllers.dto.schoolterm.SchoolTermUpdateDto;

public interface SchoolTermService {
    SchoolTermDto createSchoolTerm(SchoolTermCreateDto schoolTermCreateDto);

    SchoolTermDto updateSchoolTerm(UUID id, SchoolTermUpdateDto schoolTermUpdateDto);

    void deleteSchoolTerm(UUID id);

    SchoolTermDto getSchoolTermById(UUID id);

    Page<SchoolTermDto> getAllSchoolTerms(Pageable pageable);

}
