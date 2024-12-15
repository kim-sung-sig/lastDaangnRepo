package com.demo.daangn.app.service.user.response;

import lombok.Data;

@Data
public class UserProfileResponse {

    private String fileId;

    private String userId;

    private String fileName;

    private String fileUrl;

    private String fileExtension;

    private String fileDownloadUrl;

    private long fileSize;

}
