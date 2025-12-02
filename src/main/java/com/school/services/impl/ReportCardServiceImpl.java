package com.school.services.impl;

import com.school.controllers.dto.reportcard.ReportCardCreateDto;
import com.school.controllers.dto.reportcard.ReportCardDto;
import com.school.infra.exception.BusinessException;
import com.school.persistence.entities.Attendance;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.Grade;
import com.school.persistence.entities.ReportCard;
import com.school.persistence.entities.ReportCard.Status;
import com.school.persistence.entities.SchoolTerm;
import com.school.persistence.repositories.AttendanceRepository;
import com.school.persistence.repositories.EnrollmentRepository;
import com.school.persistence.repositories.GradeRepository;
import com.school.persistence.repositories.ReportCardRepository;
import com.school.persistence.repositories.SchoolTermRepository;
import com.school.services.ReportCardService;
import com.school.services.mapper.ReportCardMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReportCardServiceImpl implements ReportCardService {

    private final ReportCardRepository repo;
    private final ReportCardMapper mapper;
    private final EnrollmentRepository enrollmentRepo;
    private final SchoolTermRepository termRepo;
    private final GradeRepository gradeRepo;
    private final AttendanceRepository attendanceRepo;

    @Override
    public ReportCardDto create(ReportCardCreateDto dto) {
        log.info("Creating report card for enrollment {} and term {}", dto.enrollmentId(), dto.schoolTermId());
        Enrollment enrollment = enrollmentRepo.findById(dto.enrollmentId())
                .orElseThrow(() -> new BusinessException("Matrícula não encontrada"));
        SchoolTerm term = termRepo.findById(dto.schoolTermId())
                .orElseThrow(() -> new BusinessException("Período letivo não encontrado"));

        if (repo.findByEnrollmentIdAndSchoolTermId(enrollment.getId(), term.getId()).isPresent()) {
            log.warn("Report card already exists for enrollment {} and term {}", enrollment.getId(), term.getId());
            throw new BusinessException("Boletim já existe para esta matrícula/termo");
        }

        ReportCard card = mapper.toEntity(dto, enrollment, term);
        ReportCard saved = repo.save(card);
        log.info("Report card created with ID: {}", saved.getId());
        return mapper.toDto(saved);
    }

    @Override
    public ReportCardDto findById(UUID id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<ReportCardDto> listBySchoolTerm(UUID schoolTermId) {
        return repo.findBySchoolTermId(schoolTermId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<ReportCardDto> listByStudent(UUID studentId) {
        return repo.findByEnrollmentStudentId(studentId).stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<ReportCardDto> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ReportCardDto updateRemarks(UUID id, String remarks) {
        log.info("Updating remarks for report card {}", id);
        ReportCard card = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        card.setRemarks(remarks);
        return mapper.toDto(repo.save(card));
    }

    /* Job – pode ser chamado por endpoint ou scheduler */
    @Override
    public void generateForTerm(UUID schoolTermId) {
        log.info("Generating report cards for term {}", schoolTermId);
        List<Enrollment> enrollments = enrollmentRepo.findBySchoolTermId(schoolTermId);
        log.info("Found {} enrollments for term {}", enrollments.size(), schoolTermId);

        for (Enrollment en : enrollments) {
            try {
                List<Grade> grades = gradeRepo.findByEnrollmentId(en.getId());
                BigDecimal avg = grades.stream()
                        .map(g -> g.getValue().multiply(g.getWeight()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.ONE, 2, RoundingMode.HALF_EVEN);

                long totalClasses = attendanceRepo.countByEnrollmentId(en.getId());
                long present = attendanceRepo.countByEnrollmentIdAndStatus(en.getId(), Attendance.Status.PRESENT);
                BigDecimal freq = totalClasses == 0 ? BigDecimal.ZERO
                        : BigDecimal.valueOf(present)
                                .divide(BigDecimal.valueOf(totalClasses), 4, RoundingMode.HALF_EVEN)
                                .multiply(BigDecimal.valueOf(100));

                Status status = computeStatus(avg, freq);

                ReportCard card = repo.findByEnrollmentIdAndSchoolTermId(en.getId(), schoolTermId)
                        .orElse(ReportCard.builder()
                                .enrollment(en)
                                .schoolTerm(en.getSchoolClass().getSchoolTerm())
                                .build());

                card.setFinalGrade(avg);
                card.setFinalAttendance(freq);
                card.setStatus(status);
                repo.save(card);
                log.debug("Processed report card for enrollment {}", en.getId());
            } catch (Exception e) {
                log.error("Error processing report card for enrollment {}", en.getId(), e);
            }
        }
        log.info("Finished generating report cards for term {}", schoolTermId);
    }

    private Status computeStatus(BigDecimal grade, BigDecimal freq) {
        if (grade.compareTo(BigDecimal.valueOf(7)) >= 0 && freq.compareTo(BigDecimal.valueOf(75)) >= 0)
            return Status.APPROVED;
        if (grade.compareTo(BigDecimal.valueOf(5)) >= 0)
            return Status.RECOVERY;
        return Status.FAILED;
    }
}
