package com.demo.daangn.app.dao.profile;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.app.common.enums.IsUsedEnum;
import com.demo.daangn.app.domain.user.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    Optional<UserProfile> findByUserIdAndIsUsed(UUID userId, IsUsedEnum isUsed);

    Optional<UserProfile> findByUserIdAndIdAndIsUsed(UUID userId, UUID id, IsUsedEnum isUsed);

    Optional<UserProfile> findByUserIdAndId(UUID userId, UUID id);

}
