package com.demo.daangn.domain.file.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.file.entity.QFileTempEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FileTempRepositoryCustomImpl implements FileTempRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findDeleteRandomKeys(Integer isUsed, LocalDateTime createDate) {
        QFileTempEntity fileTempEntity = QFileTempEntity.fileTempEntity;
        
        return queryFactory
                .select(fileTempEntity.randomKey)
                .from(fileTempEntity)
                .where(
                    fileTempEntity.isUsed.eq(isUsed),
                    fileTempEntity.createDate.before(createDate)
                )
                .distinct()
                .fetch();
    }


}
