package com.demo.daangn.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.user.entity.DaangnUserEntity;

@Repository
public interface DaangnUserRepository extends JpaRepository<DaangnUserEntity, Long> {

    @Query("SELECT u FROM DaangnUserEntity u WHERE u.id = :id")
    Optional<DaangnUserEntity> findById(@Param("id") Long id);

    @Query("SELECT u FROM DaangnUserEntity u WHERE u.username = :username")
    Optional<DaangnUserEntity> findByUsername(@Param("username") String username);

    // @Query("SELECT u FROM DaangnUserEntity u LEFT JOIN FETCH u.chatRoomUsers WHERE u.id = :id")
    // Optional<DaangnUserEntity> findByIdWithChatRooms(@Param("id") Long id);

    // @Query("SELECT u FROM DaangnUserEntity u LEFT JOIN FETCH u.notifications WHERE u.id = :id")
    // Optional<DaangnUserEntity> findByIdWithNotifications(@Param("id") Long id);

}
