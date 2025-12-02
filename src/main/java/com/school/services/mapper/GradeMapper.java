package com.school.services.mapper;

import com.school.controllers.dto.grade.GradeCreateDto;
import com.school.controllers.dto.grade.GradeDto;
import com.school.controllers.dto.grade.GradeUpdateDto;
import com.school.persistence.entities.Enrollment;
import com.school.persistence.entities.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeMapper {

    private final EnrollmentMapper enrollmentMapper;

    public GradeDto toDto(Grade grade) {
        return new GradeDto(
                grade.getId(),
                enrollmentMapper.toDto(grade.getEnrollment()),
                grade.getTypeGrade(),
                grade.getValue(),
                grade.getWeight(),
                grade.getEvaluationDate(),
                grade.getDescription());
    }

    public Grade toEntity(Enrollment enrollment, GradeCreateDto dto) {
        return Grade.builder()
                .enrollment(enrollment)
                .typeGrade(dto.typeGrade())
                .value(dto.value())
                .weight(dto.weight() != null ? dto.weight() : java.math.BigDecimal.ONE)
                .evaluationDate(dto.evaluationDate())
                .description(dto.description())
                .build();
    }

    public void updateEntity(Grade grade, GradeUpdateDto dto) {
        if (dto.typeGrade() != null) {
            grade.setTypeGrade(dto.typeGrade());
        }
        if (dto.value() != null) {
            grade.setValue(dto.value());
        }
        if (dto.weight() != null) {
            grade.setWeight(dto.weight());
        }
        if (dto.evaluationDate() != null) {
            grade.setEvaluationDate(dto.evaluationDate());
        }
        if (dto.description() != null) {
            grade.setDescription(dto.description());
        }
    }
}
