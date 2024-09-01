package com.demo.daangn.domain.file.repository;

import java.util.List;

import com.demo.daangn.domain.file.entity.FileTempEntity;

public interface FileTempRepositoryCustom {

    // 1. randomKey로 파일 정보 조회하기
    // select * from daangn_temp_file where random_key = :

    List<FileTempEntity> findByRandomKey(String randomKey);

    // 2. randomKey로 파일 정보 삭제하기
    // delete from daangn_temp_file where random_key = :
    void deleteByRandomKey(String randomKey);

    // 3. randomKey로 파일 정보 수정하기
    // update daangn_temp_file set isUsed = :? where random
    void updateFileNameByRandomKey(String randomKey);
}
