package com.school.services.impl;

import com.school.controllers.dto.reportcard.ReportCardCreateDto;
import com.school.controllers.dto.reportcard.ReportCardDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.*;
import com.school.persistence.repositories.*;
import com.school.services.mapper.ReportCardMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportCardServiceImplTest {

    @Mock
    private ReportCardRepository repo;
    @Mock
    private ReportCardMapper mapper;
    @Mock
    private EnrollmentRepository enrollmentRepo;
    @Mock
    private SchoolTermRepository termRepo;
    @Mock
    private GradeRepository gradeRepo;
    @Mock
    private AttendanceRepository attendanceRepo;

    @InjectMocks
    private ReportCardServiceImpl service;

    @Test
    void create_ShouldCreateReportCard_WhenValid() {
        UUID enrollmentId = UUID.randomUUID();
        UUID termId = UUID.randomUUID();
        ReportCardCreateDto dto = new ReportCardCreateDto(enrollmentId, termId, BigDecimal.TEN, BigDecimal.valueOf(100),
                "Remarks");
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);
        SchoolTerm term = new SchoolTerm();
        term.setId(termId);
        ReportCard card = new ReportCard();
        ReportCardDto expectedDto = new ReportCardDto(UUID.randomUUID(), enrollmentId, "Student", "Discipline",
                "2025-1", BigDecimal.TEN, BigDecimal.valueOf(100), ReportCard.Status.APPROVED, "Remarks");

        when(enrollmentRepo.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        when(termRepo.findById(termId)).thenReturn(Optional.of(term));
        when(repo.findByEnrollmentIdAndSchoolTermId(enrollmentId, termId)).thenReturn(Optional.empty());
        when(mapper.toEntity(dto, enrollment, term)).thenReturn(card);
        when(repo.save(card)).thenReturn(card);
        when(mapper.toDto(card)).thenReturn(expectedDto);

        ReportCardDto result = service.create(dto);

        assertNotNull(result);
        verify(repo).save(card);
    }

    @Test
    void create_ShouldThrowException_WhenReportCardExists() {
        UUID enrollmentId = UUID.randomUUID();
        UUID termId = UUID.randomUUID();
        ReportCardCreateDto dto = new ReportCardCreateDto(enrollmentId, termId, BigDecimal.TEN, BigDecimal.valueOf(100),
                "Remarks");
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentId);
        SchoolTerm term = new SchoolTerm();
        term.setId(termId);

        when(enrollmentRepo.findById(enrollmentId)).thenReturn(Optional.of(enrollment));
        when(termRepo.findById(termId)).thenReturn(Optional.of(term));
        when(repo.findByEnrollmentIdAndSchoolTermId(enrollmentId, termId)).thenReturn(Optional.of(new ReportCard()));

        assertThrows(BusinessException.class, () -> service.create(dto));
        verify(repo, never()).save(any());
    }
}
