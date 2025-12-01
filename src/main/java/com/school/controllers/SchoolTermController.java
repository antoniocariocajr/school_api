package com.school.controllers;

import com.school.controllers.dto.schoolterm.SchoolTermCreateDto;
import com.school.controllers.dto.schoolterm.SchoolTermDto;
import com.school.controllers.dto.schoolterm.SchoolTermUpdateDto;
import com.school.services.SchoolTermService;
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
@RequestMapping("/api/school-terms")
@RequiredArgsConstructor
@Validated
@Tag(name = "School Terms", description = "Gestão de períodos letivos")
@SecurityRequirement(name = "oauth2")
public class SchoolTermController {

    private final SchoolTermService service;

    @GetMapping
    @Operation(summary = "Lista paginada de períodos letivos", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de períodos letivos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<SchoolTermDto> findAll(Pageable page) {
        return service.getAllSchoolTerms(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontra período letivo por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Período letivo encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Período letivo não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    public SchoolTermDto find(@PathVariable UUID id) {
        return service.getSchoolTermById(id);
    }

    @PostMapping
    @Operation(summary = "Cria novo período letivo", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Período letivo criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolTermDto create(@Valid @RequestBody SchoolTermCreateDto dto) {
        return service.createSchoolTerm(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza período letivo por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Período letivo atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Período letivo não encontrado")
    })
    @ResponseStatus(HttpStatus.OK)
    public SchoolTermDto update(@PathVariable UUID id,
            @Valid @RequestBody SchoolTermUpdateDto dto) {
        return service.updateSchoolTerm(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta período letivo por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Período letivo deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Período letivo não encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.deleteSchoolTerm(id);
    }
}
