package com.demo.daangn.domain.file.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.file.entity.FileTempEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FileTempRepositoryCustomImpl implements FileTempRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FileTempEntity> findByRandomKey(String randomKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByRandomKey'");
    }

    @Override
    public void deleteByRandomKey(String randomKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByRandomKey'");
    }

    @Override
    public void updateFileNameByRandomKey(String randomKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFileNameByRandomKey'");
    }


}
