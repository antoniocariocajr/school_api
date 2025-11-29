package com.school.services.components;

import com.school.persistence.entities.Attendance;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.ReportCard;
import com.school.persistence.repositories.AttendanceRepository;
import com.school.persistence.repositories.EnrollmentRepository;
import com.school.persistence.repositories.GradeRepository;
import com.school.persistence.repositories.ReportCardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReportCardJob {

    private final ReportCardRepository reportCardRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeRepository gradeRepository;
    private final AttendanceRepository attendanceRepository;

    @Transactional
    public void generateForTerm(UUID schoolTermId) {

        List<Enrollment> enrollments = enrollmentRepository.findBySchoolTermId(schoolTermId);

        enrollments.forEach(en -> {

            BigDecimal avg = gradeRepository.findByEnrollmentId(en.getId())
                    .stream()
                    .map(g -> g.getValue().multiply(g.getWeight()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.ONE, 2, RoundingMode.HALF_EVEN);

            List<Attendance> attendances = attendanceRepository.findByEnrollmentId(en.getId());
            BigDecimal freq = attendances
                    .stream()
                    .map(a -> a.getStatus() == Attendance.Status.PRESENT ? BigDecimal.ONE : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(attendances.size()), 4, RoundingMode.HALF_EVEN)
                    .multiply(BigDecimal.valueOf(100));

            ReportCard.Status status = computeStatus(avg, freq); // regra de aprovação

            ReportCard card = reportCardRepository
                    .findByEnrollmentIdAndSchoolTermId(en.getId(), schoolTermId)
                    .orElse(ReportCard.builder().enrollment(en).schoolTerm(en.getSchoolClass().getSchoolTerm()).build());

            card.setFinalGrade(avg);
            card.setFinalAttendance(freq);
            card.setStatus(status);
            reportCardRepository.save(card);
        });
    }

    private ReportCard.Status computeStatus(BigDecimal grade, BigDecimal freq) {
        if (grade.compareTo(BigDecimal.valueOf(7)) >= 0 && freq.compareTo(BigDecimal.valueOf(75)) >= 0)
            return ReportCard.Status.APPROVED;
        if (grade.compareTo(BigDecimal.valueOf(5)) >= 0)
            return ReportCard.Status.RECOVERY;
        return ReportCard.Status.FAILED;
    }
}
