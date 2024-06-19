package com.demo.daangn.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDaangnUserEntity is a Querydsl query type for DaangnUserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDaangnUserEntity extends EntityPathBase<DaangnUserEntity> {

    private static final long serialVersionUID = -812140622L;

    public static final QDaangnUserEntity daangnUserEntity = new QDaangnUserEntity("daangnUserEntity");

    public final SetPath<com.demo.daangn.domain.chat.entity.ChatRoomUserEntity, com.demo.daangn.domain.chat.entity.QChatRoomUserEntity> chatRoomUsers = this.<com.demo.daangn.domain.chat.entity.ChatRoomUserEntity, com.demo.daangn.domain.chat.entity.QChatRoomUserEntity>createSet("chatRoomUsers", com.demo.daangn.domain.chat.entity.ChatRoomUserEntity.class, com.demo.daangn.domain.chat.entity.QChatRoomUserEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isUsed = createNumber("isUsed", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath role = createString("role");

    public final StringPath username = createString("username");

    public final StringPath userProfile = createString("userProfile");

    public QDaangnUserEntity(String variable) {
        super(DaangnUserEntity.class, forVariable(variable));
    }

    public QDaangnUserEntity(Path<? extends DaangnUserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDaangnUserEntity(PathMetadata metadata) {
        super(DaangnUserEntity.class, metadata);
    }

}

