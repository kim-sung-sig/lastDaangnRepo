package com.demo.daangn.domain.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.file.entity.FileTempEntity;

@Repository
public interface FileTempRepository extends JpaRepository<FileTempEntity, Long> {

}
