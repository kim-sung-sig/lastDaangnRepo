package com.demo.daangn.app.service.user.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.demo.daangn.app.domain.user.User;
import com.demo.daangn.app.domain.user.UserProfile;
import com.demo.daangn.app.util.UuidUtil;

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
        UUID userId = user.getId();
        UUID profileId = userProfile.getId();

        String fileUrl = String.format("/api/v1/users/%s/profiles/%s", UuidUtil.encrypt(userId), UuidUtil.encrypt(profileId));
        String fileDownloadUrl = fileUrl + "/download";

        UserProfileResponse response = new UserProfileResponse();
        response.setFileId(profileId.toString());
        response.setUserId(userId.toString());
        response.setFileName(userProfile.getFileName());
        response.setFileUrl(fileUrl);
        response.setFileDownloadUrl(fileDownloadUrl);
        response.setFileExtension(userProfile.getFileExt());
        response.setFileSize(userProfile.getFileSize());
        response.setCreatedAt(userProfile.getCreateDate());
        return response;
    }

}
