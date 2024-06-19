package com.demo.daangn.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.follow.entity.UserFollowEntity;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollowEntity, Long> {

}
