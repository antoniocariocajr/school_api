package com.school.services.impl;

import com.school.controllers.dto.user.CreateUserRequest;
import com.school.persistence.entities.Person;
import com.school.persistence.entities.User;
import com.school.persistence.repositories.PersonRepository;
import com.school.persistence.repositories.UserRepository;
import com.school.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional    @Override
    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");

        Person person = personRepository.save(
                Person.builder()
                        .name(request.name())
                        .cpf(request.cpf())
                        .birthDate(request.birthDate())
                        .phone(request.phone())
                        .build());

        User user = User.builder()
                .person(person)
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        return userRepository.save(user);
    }
}
