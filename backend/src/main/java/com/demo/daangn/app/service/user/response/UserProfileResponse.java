package com.demo.daangn.app.service.user.response;

import java.time.LocalDateTime;
import java.util.List;
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
        if(userProfile == null) {
            return new UserProfileResponse();
        }
        User user = userProfile.getUser();
        UUID userId = user.getId();
        UUID profileId = userProfile.getId();

        String fileUrl = String.format("/api/v1/users/%s/profiles/%s", UuidUtil.encrypt(userId), UuidUtil.encrypt(profileId));
        String fileDownloadUrl = fileUrl + "/download";

        UserProfileResponse response = new UserProfileResponse();
        response.setFileId(UuidUtil.encrypt(profileId));
        response.setUserId(UuidUtil.encrypt(userId));
        response.setFileName(userProfile.getFileName());
        response.setFileUrl(fileUrl);
        response.setFileDownloadUrl(fileDownloadUrl);
        response.setFileExtension(userProfile.getFileExt());
        response.setFileSize(userProfile.getFileSize());
        response.setCreatedAt(userProfile.getCreatedAt());
        return response;
    }

    public static List<UserProfileResponse> of(List<UserProfile> userProfiles) {
        if(userProfiles == null) {
            return List.of();
        }
        return userProfiles.stream()
                .map(UserProfileResponse::of)
                .toList();
    }
}
