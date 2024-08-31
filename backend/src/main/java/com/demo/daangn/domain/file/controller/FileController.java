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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartRequest;

import com.demo.daangn.global.util.file.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {

    /*
     * 파일 입출력을 담담하는 컨트롤러
     */

    private final FileStorageService fileStorageService;

    /**
     * 파일 읽기 주소
     * @param fileName
     * @return
     */
    @GetMapping("/upload/{fileName:.+}")
    public ResponseEntity< Resource > viewMedia(@PathVariable("fileName") String fileName) {
        log.info("fileName => {}", fileName);
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.debug("여기문제터짐,, {}", fileName);
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
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
        log.info("fileName => {}", fileName);
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.debug("여기문제터짐,, {}", fileName);
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
    public ResponseEntity<Resource> streamingFile(
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.debug("여기문제터짐,, {}", fileName);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO
    // 1. 파일 임시저장
    // return randomKey
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(MultipartRequest request) {
        return null;
    }
}
