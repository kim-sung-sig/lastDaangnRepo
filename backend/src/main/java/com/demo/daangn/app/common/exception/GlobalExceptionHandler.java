package com.demo.daangn.app.common.exception;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.daangn.app.common.dto.response.RsData;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 로직 관련
    @ExceptionHandler(CustomBusinessException.class)
    public ResponseEntity<RsData< String >> handleBusinessException(CustomBusinessException e) {
        return new ResponseEntity<>(RsData.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 전역 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RsData< String >> handleException(Exception ex) {
        log.error("예상하지 않는 오류 발생.", ex);
        return new ResponseEntity<>(RsData.of("알 수 없는 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 유효성 검증 관련
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        /*
         * MethodArgumentNotValidException handler (Validation)
         * @Valid, @Validated 으로 검증 실패시 발생
         */
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("field", ((FieldError) error).getField());
            errorDetails.put("message", error.getDefaultMessage());
            errors.add(errorDetails);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 파라미터 관련
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        /*
         * MissingServletRequestParameterException handler (필수 파라미터가 없을때 발생)
         * RequestParam, PathVariable, RequestHeader, RequestBody 등의 파라미터가 없을때 발생
         */
        Map<String, String> error = new HashMap<>();
        error.put("parameter", ex.getParameterName());
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 엔티티 관련
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RsData< String >> handleEntityNotFoundExceptions(EntityNotFoundException ex) {
        /*
         * EntityNotFoundException handler (엔티티 가 없을때 발생!)
         */
        return new ResponseEntity<>(RsData.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 인증 관련
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<RsData< String >> handleAuthExceptions(AuthException ex) {
        /*
         * AuthException handler (인증 실패시 발생)
         * @PreAuthorize, @Secured, @RolesAllowed 혹은 직접 인증 실패시 발생
         */
        return new ResponseEntity<>(RsData.of(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    // 파일 관련
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<RsData< String >> handleFileNotFoundExceptions(FileNotFoundException ex) {
        /*
         * FileNotFoundException handler (File 관련 IOException)
         */
        return new ResponseEntity<>(RsData.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<RsData< String >> handleFileNotFoundExceptions(FileStorageException ex) {
        /*
         * FileStorageException handler (File 관련 IOException)
         */
        log.error("파일 작업 중 오류 발생", ex);
        return new ResponseEntity<>(RsData.of("파일 작업 중 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
