package com.demo.daangn.global.util.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.demo.daangn.global.exception.AuthException;
import com.demo.daangn.global.exception.FileStorageException;
import com.demo.daangn.global.util.common.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/** File Util Class */
@Slf4j
@Component
public class FileStorageService {

    private final Path fileStorageLocation;
    private final Path tempRootLocation;

    /**
     * 생성자
     * @param uploadDir
     * @param tempDir
     * @throws Exception
     */
    public FileStorageService(@Value("${custom.fileDirPath}") String uploadDir, @Value("${custom.fileTempDirPath}") String tempDir) throws Exception {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.tempRootLocation = Paths.get(tempDir).toAbsolutePath().normalize();

        try {
            if(!Files.exists(this.fileStorageLocation)){
                Files.createDirectories(this.fileStorageLocation); // file upload directory
            }
            if(!Files.exists(this.tempRootLocation)){
                Files.createDirectories(this.tempRootLocation); // temporary directory
            }
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * 파일 저장
     * 임시파일 디렉토리에 파일 저장하고 랜덤키 생성하여 DB에 저장
     * @param file
     * @return
     * @throws AuthException
     * @throws FileStorageException
     * @throws Exception
     */
    public FileStoreTempResponse saveTempFile(HttpServletRequest request, MultipartRequest multipartRequest) throws AuthException, FileStorageException{
        // 1. 사용자 인증 검증
        validateUser(request);

        // 2. 랜덤키 생성
        String randomKey = "V1" + UUID.randomUUID().toString() + "_" + System.currentTimeMillis();

        // 3. 파일 저장 및 랜덤키 DB 저장
        return storeFiles(multipartRequest, randomKey);
    }

    private void validateUser(HttpServletRequest request) throws AuthException {
        try {
            CommonUtil.getUser(request);
        } catch (AuthException e) {
            throw e;
        }
    }

    private FileStoreTempResponse storeFiles(MultipartRequest multipartRequest, String randomKey) throws FileStorageException {
        Path tempDir = tempRootLocation.resolve(randomKey);
        createDirectoryIfNotExists(tempDir);

        List<MultipartFile> files = multipartRequest.getFiles("file");
        if (files == null || files.isEmpty()) {
            throw new FileStorageException("No files to upload.");
        }

        List<Long> fileIds = new ArrayList<>();
        List<String> savedFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            validateFile(file);
            Long fileId = saveRandomKeyInDatabase(randomKey, file);
            String savedFileName = saveFile(file, tempDir);
            fileIds.add(fileId);
            savedFileNames.add(savedFileName);
        }

        return FileStoreTempResponse.builder()
                .randomKey(randomKey)
                .fileIds(fileIds)
                .build();
    }

    private void createDirectoryIfNotExists(Path directory) throws FileStorageException {
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            throw new FileStorageException("Could not create directory: " + directory.toString(), e);
        }
    }

    private void validateFile(MultipartFile file) throws FileStorageException {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
    }

    private String saveFile(MultipartFile file, Path tempDir) throws FileStorageException {
        String saveFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path targetLocation = tempDir.resolve(saveFileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file: " + saveFileName, e);
        }

        return saveFileName;
    }

    private Long saveRandomKeyInDatabase(String randomKey, MultipartFile file) throws FileStorageException {
        try {
            // RandomKeyEntity randomKeyEntity = new RandomKeyEntity();
            // randomKeyEntity.setKey(randomKey);
            // RandomKeyEntity savedEntity = randomKeyRepository.save(randomKeyEntity);
            // return savedEntity.getId();
            return 1L;
        } catch (Exception e) {
            throw new FileStorageException("Failed to save random key in database.", e);
        }
    }

    /**
     * 파일 저장하기
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public FileStoreResponse storeFile(MultipartFile file) throws Exception{
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String saveFileName = UUID.randomUUID() + "_" + fileName;
        log.debug("fileName => {}", fileName);
        log.debug("saveFileName => {}", saveFileName);

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(saveFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileStoreResponse fileStoreResponse = FileStoreResponse.builder()
                    .fileName(fileName)
                    .saveFileName(saveFileName)
                    .build();

            return fileStoreResponse;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * 파일 읽기
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public Resource loadFileAsResource(String fileName) throws FileNotFoundException{
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    /**
     * 파일 삭제하기
     * @param fileName
     * @throws IOException
     */
    public void deleteFile(String fileName) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Files.deleteIfExists(filePath);
    }

    /**
     * 파일 이동하기
     */
    public Path moveFile(String fileName, String targetDirPath) throws IOException {
        // 이동할 파일의 현재 경로
        Path sourcePath = this.fileStorageLocation.resolve(fileName).normalize();

        // 소스 파일이 존재하는지 확인
        if (!Files.exists(sourcePath)) {
            throw new IOException("Source file not found: " + sourcePath.toString());
        }

        // 이동할 목표 디렉토리 경로
        Path targetDir = Paths.get(targetDirPath).toAbsolutePath().normalize();
        
        // 목표 디렉토리가 없으면 생성
        if(!Files.exists(targetDir)){
            Files.createDirectories(targetDir);
        }

        // 이동할 파일의 새로운 경로
        Path targetPath = targetDir.resolve(fileName);

        targetPath.getFileName().toString();

        // 파일 이동
        return Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

}