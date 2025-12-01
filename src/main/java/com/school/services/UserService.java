package com.school.services;

import com.school.controllers.dto.user.UserDto;
import com.school.persistence.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserDto findByEmail(String email);

    Page<UserDto> findAll(Pageable pageable);

    UserDto updateRole(UUID userId, User.Role newRole);

    void deactivate(UUID userId);
}
