package com.demo.daangn.domain.tempfile.dto.response;

import java.nio.file.Path;

import com.demo.daangn.domain.tempfile.entity.TempFile;

import lombok.Data;

@Data
public class FileStoreRs {

    private String fileId;          // 파일 ID
    private String previewPath;     // 미리보기 요청 URL

    private String fileName;        // 파일명
    private long fileSize;          // 파일 크기
    private String fileExt;         // 파일 확장자


    public FileStoreRs(Path previewPath, TempFile tempFile) {
        this.fileId = tempFile.getId().toString();
        this.previewPath = previewPath.toAbsolutePath().toString();
        this.fileName = tempFile.getFileName();
        this.fileSize = tempFile.getFileSize();
        this.fileExt = tempFile.getFileExt();
    }

}
