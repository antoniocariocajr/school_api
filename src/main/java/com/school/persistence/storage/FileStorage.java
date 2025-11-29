package com.school.persistence.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    /* grava e devolve a “storage key” (caminho único) */
    String save(MultipartFile file);

    /* lê o arquivo de volta */
    Resource load(String storageKey);

    /* remove arquivo (opcional) */
    void delete(String storageKey);
}
