package com.school.controllers.dto.teacher;

import com.school.controllers.dto.person.PersonDto;
import java.time.LocalDate;
import java.util.UUID;

public record TeacherDto(
                UUID id,
                PersonDto personDto,
                String registrationCode,
                LocalDate hireDate,
                LocalDate leaveDate,
                Boolean active) {
}
