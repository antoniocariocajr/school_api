package com.school.services.impl;

import com.school.controllers.dto.document.DocumentDto;
import com.school.persistence.entities.Document;
import com.school.persistence.entities.Person;
import com.school.persistence.repositories.DocumentRepository;
import com.school.persistence.repositories.PersonRepository;
import com.school.persistence.storage.FileStorage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl {

        private final DocumentRepository docRepo;
        private final PersonRepository personRepo;
        private final FileStorage storage; // sua interface para S3 ou disco

        @Transactional
        public DocumentDto upload(UUID personId, MultipartFile file, Document.Type type, String uploadedBy) {

            Person person = personRepo.findById(personId).orElseThrow();

            String key = storage.save(file); // retorna chave única

            Document doc = Document.builder()
                    .person(person)
                    .originalName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .storageKey(key)
                    .type(type)
                    .uploadedAt(LocalDateTime.now())
                    .uploadedBy(uploadedBy)
                    .build();

            return DocumentDto.from(docRepo.save(doc));
        }

        public Resource download(UUID docId) {
            Document doc = docRepo.findById(docId).orElseThrow();
            return storage.load(doc.getStorageKey());
        }

}
