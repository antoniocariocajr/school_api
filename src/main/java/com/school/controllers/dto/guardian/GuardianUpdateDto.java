package com.school.controllers.dto.guardian;

public record GuardianUpdateDto(
        String kinship,
        Boolean financialResponsible,
        Boolean legalResponsible,
        Boolean active) {
}
