package com.demo.daangn.app.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;

public class WebpFileUtil {

    /*
     * 참조 https://velog.io/@dongjae0803/Webp로-이미지-파일-최적화하기
     */

    private static final boolean DEFAULT_OVERWRITE = false;
    private static final boolean DEFAULT_WITH_LOSSLESS = false;

    // 웹P로 변환 (손실/무손실 압축), 덮어쓰기, 리사이징 옵션
    public static Path convertToWebp(Path filePath, String fileName, boolean withLossless, boolean overwrite, int width, int height) throws IOException {
        Path sourcePath = filePath.resolve(fileName);
        String fileNameWithoutExtension = CustomFileUtil.getFileNameWithoutExtension(fileName);
        Path destinationPath = filePath.resolve(fileNameWithoutExtension + ".webp");

        // 덮어쓰기 설정 확인
        if (!overwrite && Files.exists(destinationPath)) {
            throw new IOException("File already exists and overwrite is disabled: " + destinationPath);
        }

        ImmutableImage image = ImmutableImage.loader().fromPath(sourcePath);

        // 사이즈 조정
        if(width > 0 && height > 0) {
            String sizeFolder = width + "x" + height;
            Path sizeDirectory = filePath.resolve(sizeFolder); // 사이즈 별 디렉토리 생성 경로
            Files.createDirectories(sizeDirectory); // 디렉토리가 없으면 생성
            destinationPath = sizeDirectory.resolve(fileNameWithoutExtension + ".webp");

            double aspectRatio = (double) image.width / image.height;
            if (aspectRatio > 1) {
                height = (int) (width / aspectRatio);
            } else {
                width = (int) (height * aspectRatio);
            }

            image = image.scaleTo(width, height);
        }

        // 변환 및 저장
        if (withLossless) {
            image.output(WebpWriter.DEFAULT.withLossless(), destinationPath);
        } else {
            image.output(WebpWriter.DEFAULT, destinationPath);
        }
        return destinationPath;
    }

    // 오버로드 1: 기본 무손실 여부 (DEFAULT_WITH_LOSSLESS)
    public static Path convertToWebp(Path filePath, String fileName, boolean overwrite, int width, int height) throws IOException {
        return convertToWebp(filePath, fileName, DEFAULT_WITH_LOSSLESS, overwrite, width, height);
    }

    // 오버로드 2: 리사이징 없는 기본 변환
    public static Path convertToWebp(Path filePath, String fileName, boolean overwrite) throws IOException {
        return convertToWebp(filePath, fileName, overwrite, 0, 0);
    }

    // 오버로드 3: 기본 덮어쓰기 설정 사용
    public static Path convertToWebp(Path filePath, String fileName) throws IOException {
        return convertToWebp(filePath, fileName, DEFAULT_OVERWRITE, 0, 0);
    }

    // 오버로드 4: 리사이징 변환 (손실/무손실 선택)
    public static Path convertToWebp(Path filePath, String fileName, int width, int height) throws IOException {
        return convertToWebp(filePath, fileName, DEFAULT_OVERWRITE, width, height);
    }

    // 압축률 계산
    public static double calculateCompressionRate(File originalFile, File convertedFile) {
        double originalSizeKB = originalFile.length() / 1024.0;
        double convertedSizeKB = convertedFile.length() / 1024.0;
        double compressionRatio = (convertedSizeKB / originalSizeKB) * 100;
        return 100 - compressionRatio; // 압축률 (백분율)
    }

    public static void main(String[] args) throws IOException {
        Path filePath = Path.of("/DATA/521737");
        String testFileName = "다운로드 (7).jpg";

        // 손실 압축 테스트
        Path convertedFile = convertToWebp(filePath, testFileName, true, 50, 50);

        // 압축률 계산
        File originalFile = filePath.resolve(testFileName).toFile();
        File lossFile = convertedFile.toFile();

        System.out.printf("Lossy Compression Rate: %.2f%%%n", calculateCompressionRate(originalFile, lossFile));
    }

}
