package com.demo.daangn.app.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/** QueryDSL config */
@Configuration
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }

}
