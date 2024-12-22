package com.demo.daangn.app.util;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.demo.daangn.app.common.exception.FileStorageException;
import com.demo.daangn.app.common.exception.file.CustomFileNotFoundException;

public class CustomFileUtil {

    private static final CopyOption[] DEFAULT_COPY_OPTIONS = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
    private static final long DEFAULT_MAX_SIZE = 10485760; // 10MB

    public static void createDirectories(Path directory) throws FileStorageException {
        try {
            checkPath(directory);
            Files.createDirectories(directory);
        } catch (CustomFileNotFoundException e) {
            throw new FileStorageException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileStorageException("Failed to create directory : " + directory, e);
        }
    }

    private static void checkPath(Path path) {
        if (path == null) {
            throw new CustomFileNotFoundException("Path must not be null");
        }
        if(!Files.exists(path)) {
            throw new CustomFileNotFoundException("path not found: " + path);
        }
    }

    public static void copy(Path source, Path target) {
        copy(source, target, DEFAULT_COPY_OPTIONS);
    }

    private static void copy(Path source, Path target, CopyOption... options) {
        try {
            checkPath(source);
            createDirectories(target.getParent());
            checkPath(target.getParent());
            Files.copy(source, target, options);
        } catch (CustomFileNotFoundException e) {
            throw new FileStorageException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileStorageException("Failed to copy file from " + source + " to " + target, e);
        }
    }

    public static void move(Path source, Path target) {
        move(source, target, DEFAULT_COPY_OPTIONS);
    }

    private static void move(Path source, Path target, CopyOption... options) {
        try {
            checkPath(source);
            createDirectories(target.getParent());
            checkPath(target.getParent());
            Files.move(source, target, options);
        } catch (CustomFileNotFoundException e) {
            throw new FileStorageException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileStorageException("Failed to move file from " + source + " to " + target, e);
        }
    }

    public static void delete(Path path) {
        try {
            checkPath(path);
            if (Files.isDirectory(path)) {
                try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                    for (Path entry : entries) {
                        delete(entry);
                    }
                }
            }
            Files.delete(path);
        } catch (CustomFileNotFoundException e) {
            throw new FileStorageException(e.getMessage(), e);
        } catch (DirectoryNotEmptyException e) {
            throw new FileStorageException("Directory is not empty: " + path, e);
        } catch (IOException | SecurityException e) {
            throw new FileStorageException("Failed to delete file or directory: " + path, e);
        }
    }

    public static void walkDirectory(Path directory) {
        try {
            checkPath(directory);
            Files.walk(directory).forEach(System.out::println);
        } catch (CustomFileNotFoundException e) {
            throw new FileStorageException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileStorageException("Failed to walk through directory: " + directory, e);
        }
    }

    public static String save(Path targetPath, MultipartFile file) {
        // 1. 파일 저장 경로 확인 및 디렉토리 생성
        if (targetPath == null) {
            throw new FileStorageException("Target path must not be null.");
        }

        // 2. 파일 유효성 검사
        if (file == null || file.isEmpty() || !validateFileName(file.getOriginalFilename())) {
            throw new FileStorageException("Failed to store empty or invalid file.");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!validateFileExtension(fileName, List.of(".jpg", ".jpeg", ".png", ".gif", ".webp"))) {
            throw new FileStorageException("Failed to store file with invalid extension");
        }
        if (fileName.isEmpty()) {
            throw new FileStorageException("Failed to store file with invalid name");
        }
        if (!validateFileSize(file, DEFAULT_MAX_SIZE)) {
            throw new FileStorageException("Failed to store file with invalid size");
        }

        // 3. 파일 저장
        createDirectories(targetPath); // 디렉토리가 없으면 생성
        if (!Files.isDirectory(targetPath)) {
            throw new FileStorageException("Target path is not a directory: " + targetPath);
        }
        try {
            String saveFileName = getFileNameWithoutExtension(fileName) + "_" + System.currentTimeMillis() + getFileExtension(fileName);
            Path targetLocation = targetPath.resolve(saveFileName).normalize();
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return saveFileName;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file", e);
        }
    }

    public static List<String> saveAll(Path targetPath, Collection<MultipartFile> files) {
        if(files == null || files.isEmpty()) {
            throw new FileStorageException("Failed to store empty files.");
        }
        return files.stream().map(file -> save(targetPath, file)).toList();
    }

    public static Optional<Resource> getFileResource(Path file) {
        try {
            if(file == null || !Files.exists(file)) {
                return Optional.empty();
            }

            if(Files.isDirectory(file)) {
                return Optional.empty();
            }

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return Optional.ofNullable(resource);
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Extracts the file extension from a given file name.
     * @param fileName the name of the file
     * @return the file extension, or an empty string if no extension is found
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDotIndex).toLowerCase();
    }

    // 확장자를 제거한 파일명 반환
    public static String getFileNameWithoutExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        }

        return fileName.substring(0, lastDotIndex);
    }

    // 파일명 유효성 검사
    public static boolean validateFileName(String filName) {
        if(filName == null || filName.isEmpty() || filName.contains("..")) {
            return false;
        }
        return true;
    }

    // 파일 확장자 유효성 검사
    public static boolean validateFileExtension(String fileName, Collection<String> allowedExtensions) {
        String fileExtension = getFileExtension(fileName);
        return allowedExtensions.contains(fileExtension);
    }

    public static boolean validateFileSize(MultipartFile file, long maxSize) {
        return 0 < file.getSize() && file.getSize() <= maxSize;
    }

    public static boolean validateFile(MultipartFile file, Collection<String> allowedExtensions, long maxSize) {
        return validateFileName(file.getOriginalFilename())
                && validateFileExtension(file.getOriginalFilename(), allowedExtensions)
                && validateFileSize(file, maxSize);
    }

}
