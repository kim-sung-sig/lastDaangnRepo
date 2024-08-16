package com.demo.daangn.domain.user.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.demo.daangn.domain.chat.entity.ChatRoomUserEntity;
import com.demo.daangn.domain.notification.entity.NotificationEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "my_users")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaangnUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "user_profile")
    private String userProfile;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @Column(name = "isUsed", nullable = false)
    private Integer isUsed;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatRoomUserEntity> chatRoomUsers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<NotificationEntity> notifications;


    @Override
    public String toString() {
        return "DaangnUserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userProfile='" + userProfile + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", isUsed=" + isUsed +
                '}';
    }
}
