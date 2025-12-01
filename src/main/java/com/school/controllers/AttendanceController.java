package com.school.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.controllers.dto.attendance.AttendanceCreateDto;
import com.school.controllers.dto.attendance.AttendanceDto;
import com.school.controllers.dto.attendance.AttendanceUpdateDto;
import com.school.services.AttendanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Validated
@Tag(name = "Attendance", description = "Gestão de presença")
@SecurityRequirement(name = "oauth2")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    @Operation(summary = "Lista paginada de presença", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de presença encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<AttendanceDto> findAll(Pageable page) {
        return attendanceService.findAll(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontra presença por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presença encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Presença não encontrada")
    })
    @ResponseStatus(HttpStatus.OK)
    public AttendanceDto find(@PathVariable UUID id) {
        return attendanceService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Cria presença", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Presença criada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceDto create(@Valid @RequestBody AttendanceCreateDto dto) {
        return attendanceService.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza presença", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presença atualizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Presença não encontrada")
    })
    @ResponseStatus(HttpStatus.OK)
    public AttendanceDto update(@PathVariable UUID id,
            @Valid @RequestBody AttendanceUpdateDto dto) {
        return attendanceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta presença", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Presença deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Presença não encontrada")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        attendanceService.delete(id);
    }

}
