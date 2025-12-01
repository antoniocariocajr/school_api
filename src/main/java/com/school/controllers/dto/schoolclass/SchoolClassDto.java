package com.school.controllers.dto.schoolclass;

import com.school.controllers.dto.discipline.DisciplineDto;
import com.school.controllers.dto.schoolterm.SchoolTermDto;
import com.school.controllers.dto.teacher.TeacherDto;
import com.school.persistence.entities.SchoolClass.Status;
import java.util.UUID;

public record SchoolClassDto(
        UUID id,
        DisciplineDto discipline,
        SchoolTermDto schoolTerm,
        TeacherDto teacher,
        String code,
        Integer maxStudents,
        Status status) {
}
