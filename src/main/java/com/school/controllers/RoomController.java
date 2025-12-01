package com.school.controllers;

import com.school.controllers.dto.room.RoomCreateDto;
import com.school.controllers.dto.room.RoomDto;
import com.school.controllers.dto.room.RoomUpdateDto;
import com.school.services.RoomService;
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
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Validated
@Tag(name = "Rooms", description = "Gestão de salas")
@SecurityRequirement(name = "oauth2")
public class RoomController {

    private final RoomService service;

    @GetMapping
    @Operation(summary = "Lista paginada de salas", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de salas encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.OK)
    public Page<RoomDto> findAll(Pageable page) {
        return service.getAllRooms(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontra sala por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sala encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    @ResponseStatus(HttpStatus.OK)
    public RoomDto find(@PathVariable UUID id) {
        return service.getRoom(id);
    }

    @PostMapping
    @Operation(summary = "Cria nova sala", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sala criada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDto create(@Valid @RequestBody RoomCreateDto dto) {
        return service.createRoom(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza sala por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sala atualizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    @ResponseStatus(HttpStatus.OK)
    public RoomDto update(@PathVariable UUID id,
            @Valid @RequestBody RoomUpdateDto dto) {
        return service.updateRoom(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta sala por ID", description = "Requer sessão OAuth2 (cookie)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sala deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.deleteRoom(id);
    }
}
