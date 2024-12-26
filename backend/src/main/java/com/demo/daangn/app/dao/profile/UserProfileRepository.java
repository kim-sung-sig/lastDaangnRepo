package com.demo.daangn.app.dao.profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.daangn.app.common.enums.IsUsedEnum;
import com.demo.daangn.app.domain.user.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    Optional<UserProfile> findByUserIdAndIsUsed(UUID userId, IsUsedEnum isUsed);

    @Query(value = """
            Select
                u
            From
                UserProfile u
            WHERE
                u.user.id = :userId
                AND u.isUsed != :isUsed
            ORDER BY
                CASE
                    WHEN u.isUsed = 'ENABLED' THEN 1
                    ELSE 2
                END
                , u.createdDate DESC
            """)
    List<UserProfile> findByUserIdAndIsUsedNotOrderByIsUsed(UUID userId, IsUsedEnum isUsed);

    Optional<UserProfile> findByUserIdAndIdAndIsUsed(UUID userId, UUID id, IsUsedEnum isUsed);

    Optional<UserProfile> findByUserIdAndIdAndIsUsedNot(UUID userId, UUID id, IsUsedEnum isUsed);

    Optional<UserProfile> findByUserIdAndId(UUID userId, UUID id);

}
