package com.school.controllers.dto.user;

import com.school.persistence.entities.User;

import java.util.UUID;

public record UserDto(
        UUID id,
        String email,
        User.Role role,
        boolean active,
        UUID personId,
        String personName
) {
}
