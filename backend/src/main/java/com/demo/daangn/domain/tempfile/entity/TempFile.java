package com.demo.daangn.domain.tempfile.entity;

import java.util.UUID;

import com.demo.daangn.global.dto.entity.BaseFileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(
    name = "dn_temp_file",
    indexes = {
        @Index(name = "idx_temp_file_random_key", columnList = "random_key")})
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class TempFile extends BaseFileEntity {

    /*
     * 임시파일용 데이터베이스 테이블
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 파일의 고유 ID

    @Column(name = "temp_file_uuid", unique = true, nullable = false)
    private UUID tempFileUuid; // 파일의 UUID

    @PrePersist
    public void prePersist() {
        if(this.tempFileUuid == null) {
            this.tempFileUuid = UUID.randomUUID();
        }
    }
}
