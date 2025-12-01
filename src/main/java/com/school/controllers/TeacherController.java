package com.school.controllers;

import com.school.controllers.dto.teacher.TeacherCreateDto;
import com.school.controllers.dto.teacher.TeacherDto;
import com.school.controllers.dto.teacher.TeacherUpdateDto;
import com.school.services.TeacherService;
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
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Teachers", description = "Gestão de professores")
@SecurityRequirement(name = "oauth2")
public class TeacherController {

    private final TeacherService service;

    @GetMapping
    @Operation(summary = "Lista paginada de professores", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de professores encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<TeacherDto> findAll(Pageable page) {
        return service.findAll(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontra professor por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    public TeacherDto find(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cria novo professor", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDto create(@Valid @RequestBody TeacherCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza professor por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    public TeacherDto update(@PathVariable UUID id,
            @Valid @RequestBody TeacherUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta professor por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
