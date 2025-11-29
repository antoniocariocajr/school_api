package com.school.controllers.dto.user;

import com.school.controllers.dto.person.PersonDto;
import com.school.persistence.entities.User;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(UUID id,
                           String email,
                           User.Role role,
                           boolean active,
                           Instant lastAccess //, PersonDto person
                           ) {

    public static UserResponse from(User u) {
        return new UserResponse(
                u.getId(),
                u.getEmail(),
                u.getRole(),
                u.isActive(),
                u.getLastAccess()//,PersonDto.from(u.getPerson())
        );
    }
}
