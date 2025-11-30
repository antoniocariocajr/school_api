package com.school.controllers.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PersonCreateDto(
        @NotBlank String name,
        @Email String email,
        @CPF String cpf,
        @Past LocalDate birthDate,
        String phone
) {}
