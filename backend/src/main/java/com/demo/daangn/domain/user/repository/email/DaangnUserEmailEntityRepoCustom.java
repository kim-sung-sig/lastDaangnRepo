package com.demo.daangn.domain.user.repository.email;

import java.time.LocalDateTime;
import java.util.List;

import com.demo.daangn.domain.user.entity.DaangnUserEmailEntity;

public interface DaangnUserEmailEntityRepoCustom {

    List<DaangnUserEmailEntity> findDeleteUserEmail(Integer isUsed, LocalDateTime createDate);

    DaangnUserEmailEntity findSendedUserEmail(String email);

}
