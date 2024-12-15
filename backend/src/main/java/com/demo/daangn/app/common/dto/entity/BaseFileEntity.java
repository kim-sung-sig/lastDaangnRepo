package com.demo.daangn.app.common.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@MappedSuperclass
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class BaseFileEntity extends BaseAuditEntity {

    @Column(name = "file_path", nullable = false)
    protected String filePath;

    @Column(name = "file_name", nullable = false)
    protected String fileName;

    @Column(name = "file_origin_name", nullable = false)
    protected String fileOriginName;

    @Column(name = "file_type", nullable = false)
    protected String fileType;

    @Column(name = "file_ext", nullable = false)
    protected String fileExt;

    @Column(name = "file_size", nullable = false)
    protected long fileSize;

}
