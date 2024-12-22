package com.demo.daangn.app.controller.api.temp.file;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartRequest;

import com.demo.daangn.app.common.dto.response.RsData;
import com.demo.daangn.app.common.exception.CustomBusinessException;
import com.demo.daangn.app.common.exception.FileStorageException;
import com.demo.daangn.app.service.temp.file.FileStorageService;
import com.demo.daangn.app.service.temp.file.response.FileStoreTempResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
 * 파일 입출력을 담담하는 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
@RestControllerAdvice(basePackageClasses = FileController.class)
public class FileController {


    private final FileStorageService fileStorageService;

    /**
     * 임시 파일 읽기 주소
     * @param fileName
     * @return
     */
    @GetMapping("/temp/{randomKey}/{fileName:.+}")
    public ResponseEntity<Resource> viewTempMedia(HttpServletRequest request, @PathVariable("randomKey") String randomKey, @PathVariable("fileName") String fileName) {
        return fileStorageService.loadTempFileAsResource(randomKey, fileName);
    }

    // 1. 파일 임시저장
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<RsData< FileStoreTempResponse >> uploadFile(HttpServletRequest request, MultipartRequest multipartRequest) {
        FileStoreTempResponse fileStoreTempResponse = fileStorageService.saveTempFile(multipartRequest);
        return new ResponseEntity<>(RsData.of("ok", fileStoreTempResponse), HttpStatus.OK);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<RsData<String>> handleFileStorageException(FileStorageException e) {
        return new ResponseEntity<>(RsData.of("File storage failed: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomBusinessException.class)
    public ResponseEntity<RsData<String>> handleServerBusinessException(CustomBusinessException e) {
        return new ResponseEntity<>(RsData.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 파일 스트리밍 주소
     * @param fileName
     * @param rangeHeader
     * @return
     */
    // @GetMapping("/streaming/{fileName:.+}")
    // public ResponseEntity<?> streamingFile(
    //     @PathVariable("fileName") String fileName,
    //     @RequestHeader(value = "Range", required = false) String rangeHeader
    // ) {
    //     log.info("fileName => {}", fileName);
    //     try {
    //         Resource resource = fileStorageService.loadFileAsResource(fileName);

    //         Path filePath = resource.getFile().toPath();
    //         long fileLength = resource.contentLength();

    //         long start = 0;
    //         long end = fileLength - 1;

    //         // Range 헤더를 처리하여 시작과 끝 바이트를 결정합니다.
    //         if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
    //             String[] ranges = rangeHeader.replace("bytes=", "").split("-");
    //             start = Long.parseLong(ranges[0]);
    //             if (ranges.length > 1 && !ranges[1].isEmpty()) {
    //                 end = Math.min(Long.parseLong(ranges[1]), fileLength - 1);
    //             }
    //         }

    //         // 부분 스트리밍을 위한 헤더 설정
    //         HttpHeaders headers = new HttpHeaders();
    //         headers.add("Content-Type", Files.probeContentType(filePath));
    //         headers.add("Accept-Ranges", "bytes");
    //         headers.add("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);
    //         headers.add("Content-Length", String.valueOf(end - start + 1));

    //         InputStream inputStream = resource.getInputStream();
    //         inputStream.skip(start);

    //         final long finalStart = start;
    //         final long finalEnd = end;
    //         Resource partialResource = new InputStreamResource(inputStream) {
    //             @Override
    //             public long contentLength() {
    //                 return finalEnd - finalStart + 1;
    //             }
    //         };

    //         return ResponseEntity
    //                 .status(HttpStatus.PARTIAL_CONTENT)
    //                 .headers(headers)
    //                 .body(partialResource);

    //     } catch (FileNotFoundException e) {
    //         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    //     } catch (Exception e) {
    //         log.error("Failed to load file : " + fileName, e);
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

}
