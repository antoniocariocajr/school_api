package com.school.controllers;

import com.school.controllers.dto.person.PersonCreateDto;
import com.school.controllers.dto.person.PersonDto;
import com.school.controllers.dto.person.PersonUpdateDto;
import com.school.services.PersonService;
import com.school.services.mapper.PersonMapper;
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

import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@Validated
public class PersonController {

    private final PersonService service;
    private final PersonMapper mapper;

    /* ---------- CRUD ---------- */
    @GetMapping
    public Page<PersonDto> findAll(Pageable page) {
        return service.findAll(page);
    }

    @GetMapping("/{id}")
    public PersonDto find(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto create(@Valid @RequestBody PersonCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    public PersonDto update(@PathVariable UUID id,
                            @Valid @RequestBody PersonUpdateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    /* ---------- FOTO ---------- */
    @PostMapping("/{id}/picture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadPicture(@PathVariable UUID id,
                              @RequestParam("file") MultipartFile file) {
        service.uploadPicture(id, file);
    }

    @GetMapping("/{id}/picture")
    public ResponseEntity<Resource> getPicture(@PathVariable UUID id) {
        Resource file = service.getPicture(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // ou detectar MIME
                .body(file);
    }
}
