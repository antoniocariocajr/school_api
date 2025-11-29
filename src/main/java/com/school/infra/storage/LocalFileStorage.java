package com.school.infra.storage;

import com.school.infra.exception.StorageException;
import com.school.persistence.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Profile({"dev", "test"})
@RequiredArgsConstructor
public class LocalFileStorage implements FileStorage {

    @Value("${storage.local.path:/tmp/escola-files}")
    private String basePath;

    private Path getRoot() {
        return Paths.get(basePath).toAbsolutePath().normalize();
    }

    @Override
    public String save(MultipartFile file) {
        try {
            Path root = getRoot();
            Files.createDirectories(root);

            String key = "documents/%d/%s_%s".formatted(
                    LocalDate.now().getYear(),
                    UUID.randomUUID(),
                    file.getOriginalFilename());

            Path target = root.resolve(key);
            Files.createDirectories(target.getParent());
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return key;
        } catch (IOException e) {
            throw new StorageException("Erro ao salvar arquivo localmente", e);
        }
    }

    @Override
    public Resource load(String storageKey) {
        try {
            Path file = getRoot().resolve(storageKey).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) return resource;
            throw new StorageException("Arquivo não encontrado: " + storageKey);
        } catch (MalformedURLException e) {
            throw new StorageException("Path inválido", e);
        }
    }

    @Override
    public void delete(String storageKey) {
        try {
            Files.deleteIfExists(getRoot().resolve(storageKey));
        } catch (IOException e) {
            throw new StorageException("Erro ao deletar arquivo", e);
        }
    }
}
