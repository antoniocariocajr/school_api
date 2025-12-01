package com.school.controllers.dto.guardian;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GuardianCreateDto(
        @NotNull UUID personId,
        String kinship,
        Boolean financialResponsible,
        Boolean legalResponsible) {
}
