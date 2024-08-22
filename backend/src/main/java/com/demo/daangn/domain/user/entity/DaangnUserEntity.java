package com.demo.daangn.domain.user.entity;

import java.util.List;
import java.util.UUID;

import com.demo.daangn.domain.chat.entity.ChatRoomUserEntity;
import com.demo.daangn.domain.notification.entity.NotificationEntity;
import com.demo.daangn.global.dto.entity.BaseAuditEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "my_users")
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DaangnUserEntity extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "nick_name")
    private String nickName; // seq

    @Column(name = "nick_name_seq")
    private Long nickNameSeq;

    @Column(name = "nick_name_seq_final")
    private String nickNameSeqFinal;

    @Column(name = "user_profile")
    private String userProfile;

    @Column(name = "isUsed")
    private Integer isUsed;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatRoomUserEntity> chatRoomUsers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<NotificationEntity> notifications;


    public void updateProfile(String nickName, String userProfile) {
        this.nickName = nickName;
        this.userProfile = userProfile;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

}
