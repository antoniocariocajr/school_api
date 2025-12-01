package com.school.services.mapper;

import com.school.controllers.dto.enrollment.EnrollmentCreateDto;
import com.school.controllers.dto.enrollment.EnrollmentDto;
import com.school.controllers.dto.enrollment.EnrollmentUpdateDto;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.SchoolClass;
import com.school.persistence.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EnrollmentMapper {

    private final StudentMapper studentMapper;

    public EnrollmentDto toDto(Enrollment enrollment) {
        return new EnrollmentDto(
                enrollment.getId(),
                studentMapper.toDto(enrollment.getStudent()),
                enrollment.getSchoolClass().getId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus(),
                enrollment.getFinalGrade(),
                enrollment.getFinalAttendance());
    }

    public Enrollment toEntity(Student student, SchoolClass schoolClass, EnrollmentCreateDto dto) {
        return Enrollment.builder()
                .student(student)
                .schoolClass(schoolClass)
                .enrollmentDate(LocalDate.now())
                .status(Enrollment.Status.ACTIVE)
                .build();
    }

    public void updateEntity(Enrollment enrollment, EnrollmentUpdateDto dto) {
        if (dto.status() != null) {
            enrollment.setStatus(dto.status());
        }
        if (dto.finalGrade() != null) {
            enrollment.setFinalGrade(dto.finalGrade());
        }
        if (dto.finalAttendance() != null) {
            enrollment.setFinalAttendance(dto.finalAttendance());
        }
    }
}
