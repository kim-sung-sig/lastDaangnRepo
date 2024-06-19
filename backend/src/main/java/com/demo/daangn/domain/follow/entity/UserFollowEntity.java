package com.demo.daangn.domain.follow.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

//TODO 이건 chat이 아니라 그냥 으로 이관해야댐
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserFollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follow_id", nullable = false)
    private DaangnUserEntity follow;

    @ManyToOne
    @JoinColumn(name = "folloer_id", nullable = false)
    private DaangnUserEntity folloer;

    @CreatedDate
    private LocalDateTime createDate;
}
