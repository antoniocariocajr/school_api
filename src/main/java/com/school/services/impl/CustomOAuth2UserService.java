package com.school.services.impl;

import com.school.persistence.entities.Person;
import com.school.persistence.entities.User;
import com.school.persistence.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final GuardianRepository  guardianRepository;
    private final EmployeeRepository employeeRepository;

    /* delegado para providers que não são OIDC (GitHub, Facebook ...) */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        return buildPrincipal(oAuth2User, userRequest.getClientRegistration().getRegistrationId());
    }

    /* usado para Google (OIDC) – chamado pelo SecurityConfig */
    public OAuth2User loadUser(OAuth2UserRequest userRequest, OidcUser oidcUser) {
        return buildPrincipal(oidcUser, userRequest.getClientRegistration().getRegistrationId());
    }

    private OAuth2User buildPrincipal(OAuth2User oAuth2User, String provider) {

        String email = oAuth2User.getAttribute("email");
        String name  = oAuth2User.getAttribute("name");

        if (email == null) {
            throw new OAuth2AuthenticationException("Provedor não devolveu e-mail");
        }

        /* 1. Person (dados civis) */
        Person person = personRepository.findByEmail(email)
                .orElseGet(() -> personRepository.save(
                        Person.builder()
                                .name(name)
                                .email(email)
                                .build()));

        /* 2. User (credenciais + role) */
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createDefaultUser(person, email));

        user.setLastAccess(Instant.now());
        userRepository.save(user);

        /* 3. Authorities */
        Set<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }

    private User createDefaultUser(Person person, String email) {
        /* Descobre o papel pelo domínio ou pela primeira entidade que encontrar */
        User.Role role = User.Role.STUDENT; // default

        /* Exemplo de regra simples: se existe Teacher com essa Person → TEACHER */
        if (teacherRepository.existsByPersonId(person.getId())) role = User.Role.TEACHER;
        else if (employeeRepository.existsByPersonId(person.getId())) role = User.Role.EMPLOYEE;
        else if (guardianRepository.existsByPersonId(person.getId())) role = User.Role.GUARDIAN;

        return User.builder()
                .person(person)
                .email(email)
                .role(role)
                .active(true)
                .build();
    }
}
