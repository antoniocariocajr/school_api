package com.school.controllers.dto.person;

import java.time.LocalDate;
import java.util.UUID;

public record PersonDto(
        UUID id,
        String name,
        String email,
        String cpf,
        LocalDate birthDate,
        String phone,
        String pictureUrl   // link da foto
) {}
