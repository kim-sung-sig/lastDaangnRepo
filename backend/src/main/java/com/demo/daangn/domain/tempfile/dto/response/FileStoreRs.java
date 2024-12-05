package com.demo.daangn.domain.tempfile.dto.response;

import lombok.Data;

@Data
public class FileStoreRs {

    private String fileId;

    private String fileUrl;

    public FileStoreRs( String fileId, String fileUrl) {
        this.fileId = fileId;
        this.fileUrl = fileUrl;
    }

}
