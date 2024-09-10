package com.demo.daangn.domain.user.repository.email;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.user.entity.DaangnUserEmailEntity;
import com.demo.daangn.domain.user.entity.QDaangnUserEmailEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DaangnUserEmailEntityRepoCustomImpl implements DaangnUserEmailEntityRepoCustom {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<DaangnUserEmailEntity> findDeleteUserEmail(Integer isUsed, LocalDateTime createDate) {
        QDaangnUserEmailEntity daangnUserEmailEntity = QDaangnUserEmailEntity.daangnUserEmailEntity;

        return queryFactory
                .select(daangnUserEmailEntity)
                .from(daangnUserEmailEntity)
                .where(
                    daangnUserEmailEntity.isUsed.eq(isUsed),
                    daangnUserEmailEntity.createDate.before(createDate)
                )
                .fetch();
    }

    @Override
    public DaangnUserEmailEntity findSendedUserEmail(String email) {
        QDaangnUserEmailEntity daangnUserEmailEntity = QDaangnUserEmailEntity.daangnUserEmailEntity;

        return queryFactory
                .select(daangnUserEmailEntity)
                .from(daangnUserEmailEntity)
                .where(
                    daangnUserEmailEntity.email.eq(email)
                )
                .orderBy(daangnUserEmailEntity.id.desc()) // 생성 역순 정렬
                .fetchFirst(); // 맨 처음 데이터만 가져옴
    }


}
