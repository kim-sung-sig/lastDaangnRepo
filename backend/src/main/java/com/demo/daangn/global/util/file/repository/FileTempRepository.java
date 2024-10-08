package com.demo.daangn.global.util.file.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.global.util.file.entity.FileTempEntity;

@Repository
public interface FileTempRepository extends JpaRepository<FileTempEntity, Long>, FileTempRepositoryCustom {

    List<FileTempEntity> findByRandomKey(String randomKey);

    void deleteByRandomKey(String randomKey);

}
