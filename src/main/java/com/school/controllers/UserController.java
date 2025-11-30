package com.school.controllers;

import com.school.controllers.dto.user.UserDto;
import com.school.persistence.entities.User;
import com.school.services.UserService;
import com.school.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    /* qualquer usuário logado pode ver seus próprios dados */
    @GetMapping("/me")
    public UserDto me(@AuthenticationPrincipal OAuth2User principal) {
        return service.findByEmail(principal.getName())
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* lista paginada – só admin */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDto> list(Pageable page) {
        return service.findAll(page).map(mapper::toDto);
    }

    /* altera papel – só admin */
    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto updateRole(@PathVariable UUID id, @RequestParam User.Role role) {
        return mapper.toDto(service.updateRole(id, role));
    }

    /* desativa – só admin */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }
}
