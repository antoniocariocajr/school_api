package com.school.services.impl;

import com.school.controllers.dto.document.DocumentCreateDto;
import com.school.controllers.dto.document.DocumentDto;
import com.school.persistence.entities.Document;
import com.school.persistence.entities.Person;
import com.school.persistence.repositories.DocumentRepository;
import com.school.persistence.repositories.PersonRepository;
import com.school.persistence.storage.FileStorage;
import com.school.services.mapper.DocumentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private DocumentRepository repo;
    @Mock
    private PersonRepository personRepo;
    @Mock
    private DocumentMapper mapper;
    @Mock
    private FileStorage fileStorage;

    @InjectMocks
    private DocumentServiceImpl service;

    @Test
    void upload_ShouldUploadDocument_WhenPersonExists() {
        UUID personId = UUID.randomUUID();
        MultipartFile file = mock(MultipartFile.class);
        DocumentCreateDto dto = new DocumentCreateDto(file, Document.Type.PHOTO);
        Person person = new Person();
        String key = "key";
        Document doc = new Document();
        DocumentDto expectedDto = new DocumentDto(UUID.randomUUID(), "name", "type", 100L, key, Document.Type.PHOTO,
                LocalDateTime.now(), "user");

        when(personRepo.findById(personId)).thenReturn(Optional.of(person));
        when(fileStorage.save(file)).thenReturn(key);
        when(mapper.toEntity(dto, person, key, "user")).thenReturn(doc);
        when(repo.save(doc)).thenReturn(doc);
        when(mapper.toDto(doc)).thenReturn(expectedDto);

        DocumentDto result = service.upload(personId, dto, "user");

        assertNotNull(result);
        verify(repo).save(doc);
    }

    @Test
    void upload_ShouldThrowException_WhenPersonNotFound() {
        UUID personId = UUID.randomUUID();
        MultipartFile file = mock(MultipartFile.class);
        DocumentCreateDto dto = new DocumentCreateDto(file, Document.Type.PHOTO);

        when(personRepo.findById(personId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.upload(personId, dto, "user"));
        verify(repo, never()).save(any());
    }
}
