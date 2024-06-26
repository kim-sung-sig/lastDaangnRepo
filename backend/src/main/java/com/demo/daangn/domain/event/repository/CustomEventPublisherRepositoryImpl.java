package com.demo.daangn.domain.event.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomEventPublisherRepositoryImpl {

    private final JPAQueryFactory queryFactory;

}
