package com.demo.daangn.global.util.file;

import java.io.FileNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    //@Autowired
    private final FileStorageService fileStorageService;


    /**
     * 파일 읽기 주소
     * @param fileName
     * @return
     */
    @GetMapping("/upload/{fileName:.+}")
    public ResponseEntity< ? > getFile(@PathVariable("fileName") String fileName) {
        log.info("fileName => {}", fileName);
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.debug("여기문제터짐,, {}", fileName);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
