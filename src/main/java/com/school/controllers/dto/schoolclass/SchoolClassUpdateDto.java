package com.school.controllers.dto.schoolclass;

import com.school.persistence.entities.SchoolClass.Status;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record SchoolClassUpdateDto(
        UUID teacherId,
        @Size(max = 30) String code,
        Integer maxStudents,
        Status status) {
}
