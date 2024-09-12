package com.demo.daangn.domain.user.repository.nickname;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.user.entity.DaangnUserNickNameEntity;

@Repository
public interface DaangnUserNickNameRepository extends JpaRepository<DaangnUserNickNameEntity, Long> {

    Long countByNickName(String nickName);

}
