package com.demo.daangn.domain.file.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;

import com.demo.daangn.domain.file.dto.response.FileStoreTempResponse;
import com.demo.daangn.domain.file.service.FileStorageService;
import com.demo.daangn.global.dto.response.RsData;
import com.demo.daangn.global.exception.AuthException;
import com.demo.daangn.global.exception.FileStorageException;
import com.demo.daangn.global.util.common.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
 * 파일 입출력을 담담하는 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {


    private final FileStorageService fileStorageService;

    /**
     * 임시 파일 읽기 주소
     * @param fileName
     * @return
     */
    @GetMapping("/temp/{randomKey}/{fileName:.+}")
    public ResponseEntity<?> viewTempMedia(HttpServletRequest request,
                                                    @Valid @NotEmpty @PathVariable("randomKey") String randomKey,
                                                    @Valid @NotEmpty @PathVariable("fileName") String fileName) {
        log.debug("randomeKey => {}", randomKey);
        log.debug("fileName => {}", fileName);
        try {
            Resource resource = fileStorageService.loadTempFileAsResource(randomKey, fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.debug("여기문제터짐,, {}", fileName);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 파일 읽기 주소
     * @param request
     * @param fileName
     * @return
     */
    @GetMapping("/upload/dir/**")
    public ResponseEntity<?> viewMedia2(HttpServletRequest request) {
        try {
            // `/uploaded/` 이후의 전체 경로를 가져옴
            String fullPath = request.getRequestURI().substring("/upload/dir/".length());
            log.debug("fullPath => {}", fullPath);

            // fileName을 제외한 경로 추출
            String targetDirs = fullPath.substring(0, fullPath.lastIndexOf("/"));
            String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1);
            log.debug("targetDirs => {}", targetDirs);

            // 파일 로드
            Resource resource = fileStorageService.loadFileAsResource(targetDirs, fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.debug("여기문제터짐");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 파일 다운로드 주소
     * @param fileName
     * @return
     */
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName) {
        log.info("fileName => {}", fileName);
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("여기문제터짐,, {}", fileName);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 파일 스트리밍 주소
     * @param fileName
     * @param rangeHeader
     * @return
     */
    @GetMapping("/streaming/{fileName:.+}")
    public ResponseEntity<?> streamingFile(
        @PathVariable("fileName") String fileName,
        @RequestHeader(value = "Range", required = false) String rangeHeader
    ) {
        log.info("fileName => {}", fileName);
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            Path filePath = resource.getFile().toPath();
            long fileLength = resource.contentLength();

            long start = 0;
            long end = fileLength - 1;

            // Range 헤더를 처리하여 시작과 끝 바이트를 결정합니다.
            if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                String[] ranges = rangeHeader.replace("bytes=", "").split("-");
                start = Long.parseLong(ranges[0]);
                if (ranges.length > 1 && !ranges[1].isEmpty()) {
                    end = Math.min(Long.parseLong(ranges[1]), fileLength - 1);
                }
            }

            // 부분 스트리밍을 위한 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(filePath));
            headers.add("Accept-Ranges", "bytes");
            headers.add("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
            headers.add("Content-Length", String.valueOf(end - start + 1));

            InputStream inputStream = resource.getInputStream();
            inputStream.skip(start);

            final long finalStart = start;
            final long finalEnd = end;
            Resource partialResource = new InputStreamResource(inputStream) {
                @Override
                public long contentLength() {
                    return finalEnd - finalStart + 1;
                }
            };

            return ResponseEntity
                    .status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(partialResource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Failed to load file : " + fileName, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 1. 파일 임시저장
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<RsData< FileStoreTempResponse >> uploadFile(HttpServletRequest request, MultipartRequest multipartRequest) {
        try {
            CommonUtil.getUser(request); // 로그인 확인

            // 임시 파일 저장 및 randomKey 생성
            FileStoreTempResponse fileStoreTempResponse = fileStorageService.saveTempFile(multipartRequest);

            // randomKey 반환
            return new ResponseEntity<>(RsData.of("ok", fileStoreTempResponse), HttpStatus.OK);

        } catch (AuthException e) {
            // 인증 실패 시 401 응답
            return new ResponseEntity<>(RsData.of("Unauthorized"), HttpStatus.UNAUTHORIZED);
        } catch (FileStorageException e) {
            // 파일 저장 실패 시 500 응답
            return new ResponseEntity<>(RsData.of("File storage failed: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // 기타 예외 발생 시 500 응답
            return new ResponseEntity<>(RsData.of("An unexpected error occurred: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
