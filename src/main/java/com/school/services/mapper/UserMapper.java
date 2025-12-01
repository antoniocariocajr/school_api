package com.school.services.mapper;

import com.school.controllers.dto.user.UserDto;
import com.school.persistence.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        if (user == null)
            return null;

        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getPerson().getId(),
                user.getPerson().getName());
    }
}
