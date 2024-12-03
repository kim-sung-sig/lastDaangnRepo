package com.demo.daangn.domain.usedmarket.entity;

import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Table(name = "daangn_used_market")
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder
public class UsedMarket extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnail;   // 썸네일

    private String title;       // 글제목

    private String content;     // 글내용

    private Integer price;      // 상품가격

    private String category;    // 카테고리

    private String status;      // 상품상태 (다른 테이블로 따로 뺄까?)

    private String location;    // 판매위치 좀더 세분화 하여야한다.

}
