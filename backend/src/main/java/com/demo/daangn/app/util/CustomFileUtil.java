package com.demo.daangn.app.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
public class CustomFileUtil {

    // create directory if not exists
    public static void createDirectoryIfNotExists(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
    }

    /**
     * 파일 읽기
     * @param filePath
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Optional<Resource> getFileResource(Path filePath, String fileName) {
        try {
            Path file = filePath.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return Optional.ofNullable(resource);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Save a single file
     * @param targetPath : 저장할 경로
     * @param file : 저장할 파일
     * @return
     * @throws IOException
     */
    public static String storeFile(Path targetPath, MultipartFile file) throws IOException {
        // Validate and clean the file
        MultipartFile validFile = Optional.ofNullable(file)
                .filter(f -> !f.isEmpty() && f.getSize() > 0 && f.getOriginalFilename() != null)
                .orElseThrow(() -> new IOException("Failed to store empty file"));

        // Clean the filename using StringUtils
        String fileName = Optional.ofNullable(validFile.getOriginalFilename())
                .map(StringUtils::cleanPath)
                .filter(f -> f.length() > 0)
                .orElseThrow(() -> new IOException("Failed to store file with invalid name"));

        // Generate a unique file name and define the target location
        String saveFileName = fileName;
        Path targetLocation = targetPath.resolve(saveFileName).normalize();

        // Copy the file to the target location
        try {
            Files.copy(validFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Failed to store file: " + fileName, e);
        }

        return saveFileName;
    }

    /**
     * Save multiple files
     * @param filePath
     * @param files
     * @throws IOException
     */
    public static List<String> storeFiles(Path filePath, List<MultipartFile> files) throws IOException {
        List<String> savedFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String saveFileName = storeFile(filePath, file);
            savedFileNames.add(saveFileName);
        }
        return savedFileNames;
    }

    /**
     * Move a single file
     * @param sourceDirPath
     * @param fileName
     * @param targetDirPath
     * @throws IOException
     */
    public static void moveFile(Path sourceDirPath, String fileName, Path targetDirPath) throws IOException {
        Path source = sourceDirPath.resolve(fileName).normalize();
        Path targetDir = targetDirPath.normalize();
        Path target = targetDirPath.resolve(fileName).normalize();

        createDirectoryIfNotExists(targetDir);

        Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Move multiple files
     * @param sourceDirPath
     * @param targetDirPath
     * @throws IOException
     */
    public static void moveFiles(Path sourceDirPath, Path targetDirPath) throws IOException {
        // targetDir가 존재하지 않으면 생성
        createDirectoryIfNotExists(targetDirPath);

        // 디렉토리 내용을 재귀적으로 처리
        moveFilesRecursive(sourceDirPath, targetDirPath);
    }

    /**
     * 디렉토리 내 파일 이동 (재귀적)
     * @param sourceDirPath
     * @param targetDirPath
     * @throws IOException
     */
    private static void moveFilesRecursive(Path sourceDirPath, Path targetDirPath) throws IOException {
        // 디렉토리 내 모든 엔트리를 처리
        try (var directoryStream = Files.newDirectoryStream(sourceDirPath)) {
            for (Path entry : directoryStream) {
                if (Files.isDirectory(entry)) {
                    // 디렉토리인 경우, targetDir에 동일한 디렉토리 생성 후 재귀 호출
                    Path newTargetDir = targetDirPath.resolve(sourceDirPath.relativize(entry));
                    if (!Files.exists(newTargetDir)) {
                        Files.createDirectories(newTargetDir);
                    }
                    moveFilesRecursive(entry, newTargetDir);
                } else if (Files.isRegularFile(entry)) {
                    // 파일인 경우, targetDir로 이동
                    Path targetPath = targetDirPath.resolve(entry.getFileName());
                    Files.move(entry, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }


    /**
     * Delete a single file
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    public static void deleteFile(Path filePath, String fileName) throws IOException {
        Path file = filePath.resolve(fileName).normalize();
        Files.delete(file);
    }

    /**
     * Delete files in a directory
     * @param targetDirPath
     * @return
     * @throws IOException
     */
    public static Long deleteFiles(Path targetDirPath) throws IOException {
        long deletedFilesCount = 0;

        if (Files.exists(targetDirPath) && Files.isDirectory(targetDirPath)) {
            try (var paths = Files.walk(targetDirPath)) {
                deletedFilesCount = paths
                    .map(Path::toFile)
                    .peek(File::delete)
                    .count();
            }

            Files.delete(targetDirPath); // Finally, delete the directory itself
        }
    
        return deletedFilesCount;
    }

    /**
     * Change file name
     * @param fileName
     * @return
     */
	public static String fileNameChange(String fileName) {
        return fileName
                .replaceAll( "[\\\\]" , "＼")
                .replaceAll( "[/]" , "／")
                .replaceAll( "[:]" , "：")
                .replaceAll( "[*]" , "＊")
                .replaceAll( "[?]" , "？")
                .replaceAll( "[\"]" , "＂")
                .replaceAll( "[<]" , "＜")
                .replaceAll( "[>]" , "＞")
                .replaceAll( "[|]" , "｜")
                .trim();
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
