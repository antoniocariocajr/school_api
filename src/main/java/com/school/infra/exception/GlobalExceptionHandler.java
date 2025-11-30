package com.school.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ErrorResponse handleBusiness(BusinessException ex) {
        return new ErrorResponse("BUSINESS", ex.getMessage());
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(StorageException.class)
    public ErrorResponse handleStorage(StorageException ex) {
        log.error("Storage error", ex);
        return new ErrorResponse("STORAGE", "Erro ao manipular arquivo");
    }

    public record ErrorResponse(String type, String message) {}
}
