package com.demo.daangn.domain.user.repository.nickname;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.user.entity.UserNickName;

@Repository
public interface UserNickNameRepository extends JpaRepository<UserNickName, UUID> {

    Long countByNickName(String nickName);

}
