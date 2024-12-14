package com.demo.daangn.app.service.temp.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.demo.daangn.app.common.exception.AuthException;
import com.demo.daangn.app.common.exception.CustomBusinessException;
import com.demo.daangn.app.common.exception.FileStorageException;
import com.demo.daangn.app.dao.temp.file.jpa.TempFileRepository;
import com.demo.daangn.app.domain.temp.file.TempFile;
import com.demo.daangn.app.service.temp.file.response.FileStoreRs;
import com.demo.daangn.app.service.temp.file.response.FileStoreTempResponse;
import com.demo.daangn.app.util.CustomFileUtil;

import lombok.extern.slf4j.Slf4j;

/** File Util Class */
@Slf4j
@Component
public class FileStorageService {

    private final TempFileRepository tempFileRepository;

    @Value("${custom.fileTempDirPath}")
    private String tempDir;

    private final Path tempRootLocation;

    public FileStorageService(@Value("${custom.fileTempDirPath}") String tempDir, TempFileRepository tempFileRepository) throws FileStorageException {
        this.tempFileRepository = tempFileRepository;
        this.tempRootLocation = Paths.get(tempDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.tempRootLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private final String FILE_REQUEST_PARAM = "file";
    private final String RANDOM_KEY_PREFIX = "V1_";
    private final String BASE_URL = "/api/v1/file/upload/temp";
    private final Set<String> ALLOWED_FILE_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".gif", ".webp");
    private final long MAX_FILE_SIZE = 1024 * 1024 * 10; // 10MB


    // select
    /**
     * 임시파일 읽기
     * @param randomKey
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public Resource loadTempFileAsResource(String randomKey, String fileName) throws FileNotFoundException {
        Path randomKeyTempDir = tempRootLocation.resolve(randomKey);
        Resource resource = CustomFileUtil.getFileResource(randomKeyTempDir, fileName)
                .orElseThrow(() -> new FileNotFoundException("File not found: " + fileName));
        return null;
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
    // 트랜잭션 처리하지 않음
    public FileStoreTempResponse saveTempFile(MultipartRequest multipartRequest) throws Exception {
        List<MultipartFile> files = multipartRequest.getFiles(FILE_REQUEST_PARAM);
        if(files == null || files.isEmpty()) {
            throw new CustomBusinessException("No files to upload.");
        }

        // 파일 유효성 검증
        if(files.stream().anyMatch(file -> !CustomFileUtil.validateFile(file, ALLOWED_FILE_EXTENSIONS, MAX_FILE_SIZE))) {
            throw new CustomBusinessException("Invalid file.");
        }

        // 로직 시작
        try {
            List<FileStoreRs> fileStoreRs = new ArrayList<>();
            for (MultipartFile file : files) {
                // 1. 랜덤키 생성
                UUID randomUuid = UUID.randomUUID();
                String randomKey = RANDOM_KEY_PREFIX + randomUuid.toString();

                // 2. 파일 저장
                Path randomTempDir = tempRootLocation.resolve(randomKey); // random Temp 디렉토리
                Files.createDirectories(randomTempDir);
                String savedFileName = CustomFileUtil.storeFile(randomTempDir, file);

                // 3. DB 저장
                TempFile tempEntity = saveRandomKeyInDatabase(randomUuid, randomTempDir.resolve(savedFileName), file); // DB 저장

                // 리턴용 URL 생성
                Path previewPath = Paths.get(BASE_URL, randomKey, savedFileName);
                fileStoreRs.add(new FileStoreRs(previewPath, tempEntity));
            }

            return FileStoreTempResponse.builder()
                    .files(fileStoreRs)
                    .build();

        } catch (Exception e) {
            log.error("Failed to store files.", e);
            throw new FileStorageException("Failed to store files.", e);
        }
    }

    private TempFile saveRandomKeyInDatabase(UUID randomKey, Path savedFilePath, MultipartFile file) throws FileStorageException {
        try {
            TempFile fileTempEntity = TempFile.builder()
                    .id(randomKey)
                    .filePath(savedFilePath.toAbsolutePath().toString())
                    .fileOriginName(file.getOriginalFilename())
                    .fileExt(CustomFileUtil.getFileExtension(file.getOriginalFilename()))
                    .fileType(file.getContentType()) // .jpg, .png, .gif, .webp
                    .fileSize(file.getSize())
                    .build();
            log.debug("임시저장 파일 > fileTempEntity: {}", fileTempEntity);
            return tempFileRepository.save(fileTempEntity);

        } catch (Exception e) {
            log.error("Failed to save random key in database.", e);
            throw new FileStorageException("Failed to save random key in database.", e);
        }
    }

    /**
     * 파일 실제 저장
     * 파일 이관 및 DB 수정
     * @param request
     * @param fileRequest
     * @throws AuthException
     * @throws FileStorageException
     */
    // TODO 각 서비스 에서 randomKey를 받아서 각자 처리하도록 변경
    // @Transactional
    // public void saveFiles(String randomKey, String dirName) throws FileStorageException {
    //     try {
    //         // 1. 랜덤키 유효성 검증
    //         if (randomKey == null || randomKey.isEmpty() || !randomKey.startsWith(RANDOM_KEY_PREFIX)) {
    //             throw new FileStorageException("Invalid random key.");
    //         }

    //         // 2. 랜덤키 DB 검증
    //         List<FileTempEntity> randomKeyChecker = fileTempRepository.findByRandomKey(randomKey);
    //         if(randomKeyChecker == null || randomKeyChecker.isEmpty()) {
    //             throw new FileStorageException("Random key not found: " + randomKey);
    //         }

    //         // 3. DB 수정 (isUsed = 1)
    //         // updateRandomKey(randomKey);

    //         // 4. 파일 이관
    //         Path randomKeyTempDir = tempRootLocation.resolve(randomKey);
    //         if(!Files.exists(randomKeyTempDir) || !Files.isDirectory(randomKeyTempDir)) {
    //             throw new FileStorageException("Temporary directory does not exist: " + randomKeyTempDir.toString());
    //         }
    //         Path targetDir = (dirName == null || dirName.isEmpty()) ? fileStorageLocation : fileStorageLocation.resolve(dirName);
    //         moveFiles(randomKeyTempDir, targetDir);

    //     } catch (Exception e) {
    //         log.error("Failed to save files.", e);
    //         throw new FileStorageException("Failed to save files.", e);
    //     }
    // }

    // private void moveFiles(Path sourceDirPath, Path targetDirPath) throws FileStorageException {
    //     try {
    //         CustomFileUtil.moveFiles(sourceDirPath, targetDirPath);
    //     } catch (IOException e) {
    //         log.error("Failed to move files.", e);
    //         throw new FileStorageException("Failed to move files.", e);
    //     }
    // }

    // delete 는 batch로 처리 예정
    // 일정 주기마다 create 된지 1시간이 넘었고 isUsed = 0 인 randomKey 삭제 (폴더도 삭제)

}