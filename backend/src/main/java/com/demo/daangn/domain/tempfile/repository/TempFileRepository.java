package com.demo.daangn.domain.tempfile.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.tempfile.entity.TempFile;

@Repository
public interface TempFileRepository extends JpaRepository<TempFile, Long>{

    void deleteByTempFileUuidIn(List<String> tempFileUuids);

    Optional<TempFile> findByTempFileUuid(String tempFileUuid);

    List<TempFile> findByTempFileUuidIn(List<String> uuids);

    List<TempFile> findByCreateDateBefore(LocalDateTime createDate);

}
