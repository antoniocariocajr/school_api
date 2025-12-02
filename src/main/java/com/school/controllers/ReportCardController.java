package com.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.school.controllers.dto.reportcard.ReportCardCreateDto;
import com.school.controllers.dto.reportcard.ReportCardDto;
import com.school.controllers.dto.reportcard.ReportCardUpdateDto;
import com.school.controllers.dto.user.UserDto;
import com.school.persistence.repositories.StudentRepository;
import com.school.services.ReportCardService;
import com.school.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/report-cards")
@RequiredArgsConstructor
@Tag(name = "Report Cards", description = "Boletins de alunos")
public class ReportCardController {

    private final ReportCardService service;
    private final UserService userService;
    private final StudentRepository studentRepository;

    /* ---------- CRUD ---------- */

    @PostMapping
    @Operation(summary = "Criar boletim", description = "Requer papel FINANCIAL ou ADMIN")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Boletim criado")
    @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    public ReportCardDto create(@RequestBody @Valid ReportCardCreateDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Boletim por ID")
    @SecurityRequirement(name = "bearerAuth")
    public ReportCardDto find(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping
    @Operation(summary = "Listar boletins com paginação")
    @SecurityRequirement(name = "bearerAuth")
    public Page<ReportCardDto> list(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/school-term/{schoolTermId}")
    @Operation(summary = "Boletins de um período letivo")
    @SecurityRequirement(name = "bearerAuth")
    public List<ReportCardDto> listBySchoolTerm(@PathVariable UUID schoolTermId) {
        return service.listBySchoolTerm(schoolTermId);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Boletins de um aluno")
    @SecurityRequirement(name = "bearerAuth")
    public List<ReportCardDto> listByStudent(@PathVariable UUID studentId) {
        return service.listByStudent(studentId);
    }

    /* ---------- ATUALIZAÇÃO PARCIAL ---------- */

    @PatchMapping("/{id}/remarks")
    @Operation(summary = "Atualizar observações do boletim", description = "Requer papel TEACHER ou ADMIN")
    @SecurityRequirement(name = "bearerAuth")
    public ReportCardDto updateRemarks(@PathVariable UUID id,
            @RequestBody @Valid ReportCardUpdateDto dto) {
        return service.updateRemarks(id, dto.remarks());
    }

    /* ---------- OPERAÇÕES ADMINISTRATIVAS ---------- */

    @PostMapping("/generate/{schoolTermId}")
    @Operation(summary = "Gerar boletins de um período letivo", description = "Requer papel FINANCIAL ou ADMIN")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> generate(@PathVariable UUID schoolTermId) {
        service.generateForTerm(schoolTermId);
        return ResponseEntity.ok().build();
    }

    /* ---------- BOLETIM DO ALUNO LOGADO (aluno ou responsável) ---------- */

    @GetMapping("/me")
    @Operation(summary = "Meu boletim (aluno logado)", description = "Devolve os boletins do aluno autenticado")
    @SecurityRequirement(name = "bearerAuth")
    public List<ReportCardDto> myReportCards(Authentication auth) {
        String email = auth.getName();
        UserDto user = userService.findByEmail(email);
        UUID studentId = studentRepository.findByPersonId(user.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getId();
        return service.listByStudent(studentId);
    }
}
