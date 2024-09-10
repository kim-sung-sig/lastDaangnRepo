package com.demo.daangn.domain.user.repository.email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.user.entity.DaangnUserEmailEntity;

@Repository
public interface DaangnUserEmailEntityRepository extends JpaRepository<DaangnUserEmailEntity, Long>, DaangnUserEmailEntityRepoCustom {

    // 1. 저장
    // save
    // 2. 조회

    // 3. 수정

    // 4. 삭제

}
