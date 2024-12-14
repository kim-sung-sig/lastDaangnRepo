package com.demo.daangn.app.dao.temp.nickname;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.app.domain.temp.nickname.UserNickName;

@Repository
public interface UserNickNameRepository extends JpaRepository<UserNickName, UUID> {

    Long countByNickName(String nickName);

}
