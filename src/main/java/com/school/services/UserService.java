package com.school.services;

import com.school.persistence.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
    User updateRole(UUID userId, User.Role newRole);
    void deactivate(UUID userId);
}
