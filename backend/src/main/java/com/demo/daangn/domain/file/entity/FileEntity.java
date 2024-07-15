package com.demo.daangn.domain.file.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "미정")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    private Long id;

    private String fileName;

    private String filePath;

    private String fileOriginName;

    private String fileUrl;

    private String fileType;

    private Long fileSize;

    private String fileExt;

    private String fileDownloadUrl;

    private String fileThumbnailUrl;

    private String fileThumbnailPath;

    private String fileThumbnailName;

    private String fileThumbnailExt;

    private Long fileThumbnailSize;
    
}
