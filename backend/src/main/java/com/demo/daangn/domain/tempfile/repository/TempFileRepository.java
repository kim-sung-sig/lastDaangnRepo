package com.demo.daangn.domain.tempfile.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.tempfile.entity.TempFile;

@Repository
public interface TempFileRepository extends JpaRepository<TempFile, UUID>{

    void deleteByIdIn(List<UUID> ids);

    Optional<TempFile> findById(UUID id);

    List<TempFile> findByIdIn(List<UUID> ids);

    List<TempFile> findByCreateDateBefore(LocalDateTime createDate);

}