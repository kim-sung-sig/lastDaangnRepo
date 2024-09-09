package com.demo.daangn.domain.user.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Table(
    name = "daangn_user_profiles",
    indexes = {
        @Index(name = "idx_daangn_user_profiles_user_id", columnList = "user_id"),
        @Index(name = "idx_daangn_user_profiles_file_name", columnList = "file_name")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DaangnUserProfile extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DaangnUserEntity user;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_origin_name", nullable = false)
    private String fileOriginName;

    @Column(name = "file_type")
    private String fileType; // 파일의 타입 (예: 이미지, 비디오 등)
    @Column(name = "file_ext")
    private String fileExt; // 파일의 확장자 (예: jpg, png 등)

    @Column(name = "file_size")
    private Long fileSize; // 파일의 크기 (바이트 단위)

    @PrePersist
    public void prePersist() {
        if (this.getIsUsed() == null) {
            this.setIsUsed(1);
        }
    }

}
