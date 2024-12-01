package com.demo.daangn.domain.file.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
    name = "temp_file",
    indexes = {
        @Index(name = "idx_temp_file_random_key", columnList = "random_key")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileTempEntity extends BaseAuditEntity {

    /*
     * 임시파일용 데이터베이스 테이블
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 파일의 고유 ID

    @Column(name = "random_key", unique = true, nullable = false)
    private String randomKey; // 파일의 랜덤 키

    @Column(name = "file_path", nullable = false)
    private String filePath; // 파일의 경로

    @Column(name = "file_name", nullable = false)
    private String fileName; // 파일의 이름
    @Column(name = "file_origin_name", nullable = false)
    private String fileOriginName; // 원본 파일의 이름

    @Column(name = "file_type")
    private String fileType; // 파일의 타입 (예: 이미지, 비디오 등)
    @Column(name = "file_ext")
    private String fileExt; // 파일의 확장자 (예: jpg, png 등)

    @Column(name = "file_size")
    private Long fileSize; // 파일의 크기 (바이트 단위)

    @Column(name = "is_used", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Integer isUsed; // 사용 여부 (0: 사용 안함, 1: 사용 중)

    @PrePersist
    public void prePersist() {
        if (this.isUsed == null) {
            this.isUsed = 0;
        }
        log.debug("prePersist isUsed: {}", this.getIsUsed());
    }

}
