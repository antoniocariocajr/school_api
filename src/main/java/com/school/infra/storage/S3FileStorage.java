package com.school.infra.storage;

import com.school.infra.exception.StorageException;
import com.school.persistence.storage.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Profile("prod")
@RequiredArgsConstructor
public class S3FileStorage implements FileStorage {

    private final S3Client s3Client;

    @Value("${storage.s3.bucket}")
    private String bucket;

    @Value("${storage.s3.prefix:documents}")
    private String prefix; // pasta dentro do bucket

    @Override
    public String save(MultipartFile file) {
        String key = "%s/%d/%s_%s".formatted(prefix,
                LocalDate.now().getYear(),
                UUID.randomUUID(),
                file.getOriginalFilename());

        try (InputStream is = file.getInputStream()) {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(is, file.getSize()));
        } catch (IOException e) {
            throw new StorageException("Erro ao enviar arquivo para S3", e);
        }
        return key;
    }

    @Override
    public Resource load(String storageKey) {
         try {
            GetObjectRequest getReq = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(storageKey)
                    .build();
            ResponseInputStream<GetObjectResponse> stream = s3Client.getObject(getReq);
            return new InputStreamResource(stream);
        } catch (Exception e) {
            throw new StorageException("Arquivo não encontrado no S3", e);
        }
    }

    @Override
    public void delete(String storageKey) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(storageKey)
                .build());
    }
}
