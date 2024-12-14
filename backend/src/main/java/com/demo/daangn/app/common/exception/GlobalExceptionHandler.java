package com.demo.daangn.app.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.daangn.app.common.dto.response.RsData;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomBusinessException.class)
    public ResponseEntity<String> handleBusinessException(CustomBusinessException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomSystemException.class)
    public ResponseEntity<String> handleSystemException(CustomSystemException e) {
        return new ResponseEntity<>("시스템 에러가 발생했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RsData< String >> handleException(Exception ex) {
        log.error("알 수 없는 오류가 발생했습니다. \n {}", ex);
        return new ResponseEntity<>(RsData.of("알 수 없는 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * MethodArgumentNotValidException handler (Validation)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
        .forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * EntityNotFoundException handler (엔티티 가 없을때 발생!)
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RsData< String >> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
        return new ResponseEntity<>(RsData.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * AuthException handler (너의 권한이 아닐때발생)
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<RsData< String >> handleAuthExceptions(AuthException ex) {
        return new ResponseEntity<>(RsData.of(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * FileStorageException handler (없는 파일일때)
     */
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<RsData< String >> handleFileNotFoundExceptions(FileStorageException ex) {
        return new ResponseEntity<>(RsData.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
