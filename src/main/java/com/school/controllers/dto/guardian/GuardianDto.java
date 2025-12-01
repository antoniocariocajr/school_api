package com.school.controllers.dto.guardian;

import com.school.controllers.dto.person.PersonDto;
import java.util.UUID;

public record GuardianDto(
        UUID id,
        PersonDto person,
        String kinship,
        Boolean financialResponsible,
        Boolean legalResponsible,
        Boolean active) {
}
