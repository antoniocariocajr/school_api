package com.school.services.impl;

import com.school.controllers.dto.user.UserDto;
import com.school.persistence.entities.User;
import com.school.persistence.repositories.UserRepository;
import com.school.services.UserService;
import com.school.services.mapper.UserMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserDto findByEmail(String email) {
        return mapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::toDto);
    }

    @Transactional
    @Override
    public UserDto updateRole(UUID userId, User.Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setRole(newRole);
        return mapper.toDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deactivate(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setActive(false);
        userRepository.save(user);
    }
}
