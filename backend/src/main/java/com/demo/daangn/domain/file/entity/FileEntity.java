package com.demo.daangn.domain.file.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.demo.daangn.domain.file.enums.FileEnums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "daangn_files")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 파일의 고유 ID

    @Enumerated(EnumType.STRING)
    @Column(name = "types", nullable = false)
    private FileEnums types;

    @Column(name = "file_name")
    private String fileName; // 파일의 이름
    @Column(name = "file_origin_name")
    private String fileOriginName; // 원본 파일의 이름

    @Column(name = "file_path")
    private String filePath; // 파일의 경로 (/DATA/file/upload/)
    @Column(name = "file_url")
    private String fileUrl; // 파일의 URL (/api/v1/upload/{saveFileName})

    @Column(name = "file_type")
    private String fileType; // 파일의 타입 (예: 이미지, 비디오 등)
    @Column(name = "file_ext")
    private String fileExt; // 파일의 확장자 (예: jpg, png 등)

    @Column(name = "file_size")
    private Long fileSize; // 파일의 크기 (바이트 단위)

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
