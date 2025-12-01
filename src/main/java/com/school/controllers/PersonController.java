package com.school.controllers;

import com.school.controllers.dto.person.PersonCreateDto;
import com.school.controllers.dto.person.PersonDto;
import com.school.controllers.dto.person.PersonUpdateDto;
import com.school.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@Validated
@Tag(name = "Persons", description = "Gestão de pessoas")
@SecurityRequirement(name = "oauth2")
public class PersonController {

        private final PersonService service;

        /* ---------- CRUD ---------- */
        @GetMapping
        @Operation(summary = "Lista paginada de pessoas", description = "Requer sessão OAuth2 (cookie)")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Lista de pessoas encontrada"),
                        @ApiResponse(responseCode = "401", description = "Não autenticado"),
                        @ApiResponse(responseCode = "403", description = "Não autorizado")
        })
        @ResponseStatus(HttpStatus.OK)
        public Page<PersonDto> findAll(Pageable page) {
                return service.findAll(page);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Encontra pessoa por ID", description = "Requer sessão OAuth2 (cookie)")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
                        @ApiResponse(responseCode = "401", description = "Não autenticado"),
                        @ApiResponse(responseCode = "403", description = "Não autorizado"),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        })
        @ResponseStatus(HttpStatus.OK)
        public PersonDto find(@PathVariable UUID id) {
                return service.findById(id);
        }

        @PostMapping
        @Operation(summary = "Cria nova pessoa", description = "Requer sessão OAuth2 (cookie)")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Não autenticado"),
                        @ApiResponse(responseCode = "403", description = "Não autorizado"),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        })
        @ResponseStatus(HttpStatus.CREATED)
        public PersonDto create(@Valid @RequestBody PersonCreateDto dto) {
                return service.create(dto);
        }

        @PatchMapping("/{id}")
        @Operation(summary = "Atualiza pessoa por ID", description = "Requer sessão OAuth2 (cookie)")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Não autenticado"),
                        @ApiResponse(responseCode = "403", description = "Não autorizado"),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        })
        @ResponseStatus(HttpStatus.OK)
        public PersonDto update(@PathVariable UUID id,
                        @Valid @RequestBody PersonUpdateDto dto) {
                return service.update(id, dto);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Deleta pessoa por ID", description = "Requer sessão OAuth2 (cookie)")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Não autenticado"),
                        @ApiResponse(responseCode = "403", description = "Não autorizado"),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(@PathVariable UUID id) {
                service.delete(id);
        }

        /* ---------- FOTO ---------- */
        @PostMapping("/{id}/picture")
        @Operation(summary = "Upload de foto", description = "Requer sessão OAuth2 (cookie)")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = "Foto carregada com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Não autenticado"),
                        @ApiResponse(responseCode = "403", description = "Não autorizado"),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void uploadPicture(@PathVariable UUID id,
                        @RequestParam("file") MultipartFile file) {
                service.uploadPicture(id, file);
        }

        @GetMapping("/{id}/picture")
        @Operation(summary = "Download de foto", description = "Requer sessão OAuth2 (cookie)")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Foto baixada com sucesso"),
                        @ApiResponse(responseCode = "401", description = "Não autenticado"),
                        @ApiResponse(responseCode = "403", description = "Não autorizado"),
                        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
        })
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Resource> getPicture(@PathVariable UUID id) {
                Resource file = service.getPicture(id);
                return ResponseEntity.ok()
                                .contentType(MediaType.IMAGE_JPEG) // ou detectar MIME
                                .body(file);
        }
}
