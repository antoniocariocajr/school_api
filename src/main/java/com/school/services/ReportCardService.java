package com.school.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.school.controllers.dto.reportcard.ReportCardCreateDto;
import com.school.controllers.dto.reportcard.ReportCardDto;

public interface ReportCardService {
    ReportCardDto create(ReportCardCreateDto dto);

    ReportCardDto findById(UUID id);

    List<ReportCardDto> listBySchoolTerm(UUID schoolTermId);

    List<ReportCardDto> listByStudent(UUID studentId);

    Page<ReportCardDto> findAll(Pageable pageable);

    ReportCardDto updateRemarks(UUID id, String remarks);

    void generateForTerm(UUID schoolTermId); // job
}
