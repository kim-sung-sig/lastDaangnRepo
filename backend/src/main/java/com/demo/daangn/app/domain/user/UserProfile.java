package com.demo.daangn.app.domain.user;

import java.util.UUID;

import com.demo.daangn.app.common.dto.entity.BaseFileEntity;
import com.demo.daangn.app.common.enums.IsUsedEnum;
import com.demo.daangn.app.domain.temp.file.TempFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Table(
    name = "dn_user_profiles",
    indexes = {
        @Index(name = "idx_user_profiles_user_id", columnList = "user_id")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class UserProfile extends BaseFileEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_used")
    @Enumerated(EnumType.STRING)
    private IsUsedEnum isUsed;

    @PrePersist
    public void prePersist() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
        if (this.isUsed == null) {
            this.isUsed = IsUsedEnum.ENABLED;
        }
    }

    public void ENABLED() {
        this.isUsed = IsUsedEnum.ENABLED;
    }

    public void DISABLED() {
        this.isUsed = IsUsedEnum.DISABLED;
    }

    public UserProfile(UUID id, String savedPath, User user, TempFile tempFile) {
        super();
        this.id = id;
        this.filePath = savedPath;

        this.user = user;

        this.fileName = tempFile.getFileName();
        this.fileOriginName = tempFile.getFileOriginName();
        this.fileType = tempFile.getFileType();
        this.fileExt = tempFile.getFileExt();
        this.fileSize = tempFile.getFileSize();
    }

}
