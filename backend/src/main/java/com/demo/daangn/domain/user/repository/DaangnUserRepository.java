package com.demo.daangn.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;

@Repository
public interface DaangnUserRepository extends JpaRepository<DaangnUserEntity, Long> {

    Optional<DaangnUserEntity> findByUsername(String username);
}
