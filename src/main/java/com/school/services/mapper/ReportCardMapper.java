package com.school.services.mapper;

import org.springframework.stereotype.Component;

import com.school.controllers.dto.reportcard.ReportCardCreateDto;
import com.school.controllers.dto.reportcard.ReportCardDto;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.ReportCard;
import com.school.persistence.entities.SchoolTerm;

@Component
public class ReportCardMapper {

    public ReportCardDto toDto(ReportCard r) {
        return new ReportCardDto(
                r.getId(),
                r.getEnrollment().getId(),
                r.getEnrollment().getStudent().getPerson().getName(),
                r.getEnrollment().getSchoolClass().getDiscipline().getName(),
                r.getSchoolTerm().getCode(),
                r.getFinalGrade(),
                r.getFinalAttendance(),
                r.getStatus(),
                r.getRemarks());
    }

    public ReportCard toEntity(ReportCardCreateDto dto, Enrollment enrollment, SchoolTerm term) {
        return ReportCard.builder()
                .enrollment(enrollment)
                .schoolTerm(term)
                .finalGrade(dto.finalGrade())
                .finalAttendance(dto.finalAttendance())
                .status(ReportCard.Status.IN_PROGRESS)
                .remarks(dto.remarks())
                .build();
    }
}
