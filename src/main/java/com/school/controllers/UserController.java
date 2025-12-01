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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
@Validated
@SecurityRequirement(name = "oauth2")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    /* qualquer usuário logado pode ver seus próprios dados */
    @GetMapping("/me")
    @Operation(summary = "Dados do usuário logado", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UserDto me(@AuthenticationPrincipal OAuth2User principal) {
        return service.findByEmail(principal.getName())
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* lista paginada – só admin */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista paginada de usuários", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    public Page<UserDto> list(Pageable page) {
        return service.findAll(page).map(mapper::toDto);
    }

    /* altera papel – só admin */
    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Altera papel do usuário", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role alterado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UserDto updateRole(@PathVariable UUID id, @RequestParam User.Role role) {
        return mapper.toDto(service.updateRole(id, role));
    }

    /* desativa – só admin */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desativa usuário", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public void deactivate(@PathVariable UUID id) {
        service.deactivate(id);
    }
}
