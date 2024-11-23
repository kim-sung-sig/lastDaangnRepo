package com.demo.daangn.domain.user.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    // @Query("SELECT u FROM DaangnUserEntity u LEFT JOIN FETCH u.chatRoomUsers WHERE u.id = :id")
    // Optional<DaangnUserEntity> findByIdWithChatRooms(@Param("id") Long id);

    // @Query("SELECT u FROM DaangnUserEntity u LEFT JOIN FETCH u.notifications WHERE u.id = :id")
    // Optional<DaangnUserEntity> findByIdWithNotifications(@Param("id") Long id);

}
