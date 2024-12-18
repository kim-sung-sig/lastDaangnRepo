package com.demo.daangn.app.service.user.response;

import java.time.LocalDateTime;

import com.demo.daangn.app.domain.user.User;
import com.demo.daangn.app.domain.user.UserProfile;

import lombok.Data;

@Data
public class UserProfileResponse {

    private String fileId;

    private String userId;

    private String fileName;

    private String fileUrl;

    private String fileDownloadUrl;

    private String fileExtension;

    private long fileSize;

    private LocalDateTime createdAt;

    public static UserProfileResponse of(UserProfile userProfile) {
        User user = userProfile.getUser();
        String userId = user.getId().toString();
        String profileId = userProfile.getId().toString();

        String fileUrl = String.format("/api/v1/users/%s/profiles/%s", userId, profileId);
        String fileDownloadUrl = fileUrl + "/download";

        UserProfileResponse response = new UserProfileResponse();
        response.setFileId(profileId);
        response.setUserId(userId);
        response.setFileName(userProfile.getFileName());
        response.setFileUrl(fileUrl);
        response.setFileDownloadUrl(fileDownloadUrl);
        response.setFileExtension(userProfile.getFileExt());
        response.setFileSize(userProfile.getFileSize());
        response.setCreatedAt(userProfile.getCreateDate());
        return response;
    }

}
