package com.school.controllers.dto.user;

import com.school.persistence.entities.User;

import java.time.LocalDate;

public record CreateUserRequest(
        String name,
        String email,
        String password,
        String cpf,
        String phone,
        LocalDate birthDate,
        User.Role role
) {
}
