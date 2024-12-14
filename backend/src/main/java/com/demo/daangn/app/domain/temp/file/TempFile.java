package com.demo.daangn.app.domain.temp.file;

import java.util.UUID;

import com.demo.daangn.app.common.dto.entity.BaseFileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "id", unique = true, nullable = false)
    private UUID id; // 파일의 UUID

    @PrePersist
    private void prePersist() {
        if(this.id == null) this.id = UUID.randomUUID();
    }
}
