package com.school.infra.security;

import com.school.persistence.entities.Person;
import com.school.persistence.entities.User;
import com.school.persistence.repositories.PersonRepository;
import com.school.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor @Slf4j
public class AdminBootstrap implements ApplicationRunner {

    private final PersonRepository personRepo;
    private final UserRepository userRepo;

    @Value("${admin.email:admin@escola.local}")
    private String adminEmail;

    @Value("${admin.name:System Admin}")
    private String adminName;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepo.existsByEmail(adminEmail)) return; // já existe

        Person person = personRepo.save(
                Person.builder()
                        .name(adminName)
                        .email(adminEmail)
                        .build());

        User admin = User.builder()
                .person(person)
                .email(adminEmail)
                .role(User.Role.ADMIN)
                .createdAt(Instant.now())
                .active(true)
                .build();

        userRepo.save(admin);
        log.info("Admin criado: {}", adminEmail);
    }
}
