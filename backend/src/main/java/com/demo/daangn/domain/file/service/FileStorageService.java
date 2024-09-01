package com.demo.daangn.domain.file.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.demo.daangn.global.exception.AuthException;
import com.demo.daangn.global.exception.FileStorageException;
import com.demo.daangn.global.util.file.CustomFileUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/** File Util Class */
@Slf4j
@Component
public class FileStorageService {

    private final Path fileStorageLocation;
    private final Path tempRootLocation;

    private final String RANDOM_KEY_PREFIX = "V1_";

    private final String FILE_REQUEST_PARAM = "file";

    /**
     * 생성자
     * @param uploadDir
     * @param tempDir
     * @throws Exception
     */
    public FileStorageService(@Value("${custom.fileDirPath}") String uploadDir,
                              @Value("${custom.fileTempDirPath}") String tempDir) throws FileStorageException {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.tempRootLocation = Paths.get(tempDir).toAbsolutePath().normalize();

        try {
            CustomFileUtil.createDirectoryIfNotExists(this.fileStorageLocation);
            CustomFileUtil.createDirectoryIfNotExists(this.tempRootLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    // select
    /**
     * 파일 읽기
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public Resource loadFileAsResource(String fileName) throws FileNotFoundException {
        return CustomFileUtil.getFileResource(fileStorageLocation, fileName);
    }

    // insert
    /**
     * 파일 임시 저장
     * 임시파일 디렉토리에 파일 저장하고 랜덤키 생성하여 DB에 저장
     * @param file
     * @return
     * @throws AuthException
     * @throws FileStorageException
     * @throws Exception
     */
    public FileStoreTempResponse saveTempFile(HttpServletRequest request, MultipartRequest multipartRequest) throws FileStorageException {
        try {
            // 1. 랜덤키 생성
            String randomKey = RANDOM_KEY_PREFIX + UUID.randomUUID().toString() + "_" + System.currentTimeMillis();

            Path randomTempDir = tempRootLocation.resolve(randomKey); // random Temp 디렉토리
            CustomFileUtil.createDirectoryIfNotExists(randomTempDir); // 파일 디렉토링 생성

            List<MultipartFile> files = multipartRequest.getFiles(FILE_REQUEST_PARAM);
            if(files == null || files.isEmpty()) {
                throw new FileStorageException("No files to upload.");
            }

            List<Long> fileIds = new ArrayList<>();
            List<String> savedFileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                validateFile(file);
                Long fileId = saveRandomKeyInDatabase(randomKey, file); // DB 저장
                String savedFileName = CustomFileUtil.storeFile(randomTempDir, file);
                fileIds.add(fileId);
                savedFileNames.add(savedFileName);
            }

            return FileStoreTempResponse.builder()
                    .randomKey(randomKey)
                    .fileIds(fileIds)
                    .build();

        } catch (Exception e) {
            log.error("Failed to store files.", e);
            throw new FileStorageException("Failed to store files.", e);
        }
    }

    private void validateFile(MultipartFile file) throws FileStorageException {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence => " + fileName);
        }
    }

    private Long saveRandomKeyInDatabase(String randomKey, MultipartFile file) throws FileStorageException {
        try {
            // RandomKeyEntity randomKeyEntity = new RandomKeyEntity();
            // randomKeyEntity.setKey(randomKey);
            // RandomKeyEntity savedEntity = randomKeyRepository.save(randomKeyEntity);
            // return savedEntity.getId();
            return 1L;
        } catch (Exception e) {
            log.error("Failed to save random key in database.", e);
            throw new FileStorageException("Failed to save random key in database.", e);
        }
    }

    // update
    /**
     * 파일 실제 저장
     * 파일 이관 및 DB 수정
     * @param request
     * @param fileRequest
     * @throws AuthException
     * @throws FileStorageException
     */
    @Transactional
    public void saveFiles(String randomKey) throws FileStorageException {
        try {
            // 1. 랜덤키 유효성 검증
            if (randomKey == null || randomKey.isEmpty() || !randomKey.startsWith(RANDOM_KEY_PREFIX)) {
                throw new FileStorageException("Invalid random key.");
            }

            // 2. 랜덤키 DB 검증
            // RandomKeyEntity randomKeyEntity = randomKeyRepository.findByKey(randomKey)
            //         .orElseThrow(() -> new FileStorageException("Random key not found: " + randomKey));

            // 3. DB 수정 (isUsed = 1)
            // updateRandomKey(randomKey);

            // 4. 파일 이관
            Path randomKeyTempDir = tempRootLocation.resolve(randomKey);
            if(!Files.exists(randomKeyTempDir) || !Files.isDirectory(randomKeyTempDir)) {
                throw new FileStorageException("Temporary directory does not exist: " + randomKeyTempDir.toString());
            }
            moveFiles(randomKeyTempDir, fileStorageLocation);

        } catch (Exception e) {
            log.error("Failed to save files.", e);
            throw new FileStorageException("Failed to save files.", e);
        }
    }

    private void moveFiles(Path sourceDirPath, Path targetDirPath) throws FileStorageException {
        try {
            CustomFileUtil.moveFiles(sourceDirPath, targetDirPath);
        } catch (IOException e) {
            log.error("Failed to move files.", e);
            throw new FileStorageException("Failed to move files.", e);
        }
    }

    // delete 는 batch로 처리 예정
    // 일정 주기마다 create 된지 1시간이 넘었고 isUsed = 0 인 randomKey 삭제 (폴더도 삭제)

}