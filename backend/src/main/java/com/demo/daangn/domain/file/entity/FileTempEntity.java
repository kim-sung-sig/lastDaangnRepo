package com.demo.daangn.domain.file.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "daangn_temp_file")
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

}