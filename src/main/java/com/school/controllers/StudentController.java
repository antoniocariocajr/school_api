package com.school.controllers;

import com.school.controllers.dto.student.StudentCreateDto;
import com.school.controllers.dto.student.StudentDto;
import com.school.controllers.dto.student.StudentUpdateDto;
import com.school.services.StudentService;
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
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Validated
@Tag(name = "Students", description = "Gestão de estudantes")
@SecurityRequirement(name = "oauth2")
public class StudentController {

    private final StudentService service;

    @GetMapping
    @Operation(summary = "Lista paginada de estudantes", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de estudantes encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<StudentDto> findAll(Pageable page) {
        return service.findAll(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontra estudante por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudante encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    public StudentDto find(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cria novo estudante", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estudante criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto create(@Valid @RequestBody StudentCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza estudante por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudante atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    public StudentDto update(@PathVariable UUID id,
            @Valid @RequestBody StudentUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta estudante por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estudante deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
