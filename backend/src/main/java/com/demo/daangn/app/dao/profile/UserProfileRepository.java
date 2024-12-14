package com.demo.daangn.app.dao.profile;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.daangn.app.domain.user.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

}
