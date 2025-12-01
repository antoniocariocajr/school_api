package com.school.controllers;

import com.school.controllers.dto.guardian.GuardianCreateDto;
import com.school.controllers.dto.guardian.GuardianDto;
import com.school.controllers.dto.guardian.GuardianUpdateDto;
import com.school.services.GuardianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/guardians")
@RequiredArgsConstructor
@Validated
@Tag(name = "Guardians", description = "Gestão de responsáveis")
@SecurityRequirement(name = "oauth2")
public class GuardianController {

    private final GuardianService service;

    @GetMapping
    @Operation(summary = "Lista paginada de responsáveis", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de responsáveis encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<GuardianDto> findAll(Pageable page) {
        return service.findAll(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontra responsável por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsável encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    public GuardianDto find(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cria novo responsável", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Responsável criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public GuardianDto create(@Valid @RequestBody GuardianCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza responsável por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Responsável atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    public GuardianDto update(@PathVariable UUID id,
            @Valid @RequestBody GuardianUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta responsável por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Responsável deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Responsável não encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
