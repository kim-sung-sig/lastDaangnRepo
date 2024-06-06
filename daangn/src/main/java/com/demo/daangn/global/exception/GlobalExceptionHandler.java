package com.demo.daangn.global.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.daangn.global.dto.response.RsData;


@RestControllerAdvice
public class GlobalExceptionHandler {
    
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * EntityNotFoundException handler
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

}
