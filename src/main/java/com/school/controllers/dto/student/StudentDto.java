package com.school.controllers.dto.student;

import com.school.controllers.dto.person.PersonDto;
import java.time.LocalDate;
import java.util.UUID;

public record StudentDto(
        UUID id,
        PersonDto person,
        String enrollmentNumber,
        LocalDate entryDate,
        LocalDate exitDate,
        String originSchool,
        Boolean active) {
}
