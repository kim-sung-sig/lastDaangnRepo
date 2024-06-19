package com.demo.daangn.domain.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomEntity is a Querydsl query type for ChatRoomEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomEntity extends EntityPathBase<ChatRoomEntity> {

    private static final long serialVersionUID = 1994956822L;

    public static final QChatRoomEntity chatRoomEntity = new QChatRoomEntity("chatRoomEntity");

    public final StringPath chatRoomCd = createString("chatRoomCd");

    public final SetPath<ChatRoomUserEntity, QChatRoomUserEntity> chatRoomUsers = this.<ChatRoomUserEntity, QChatRoomUserEntity>createSet("chatRoomUsers", ChatRoomUserEntity.class, QChatRoomUserEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public QChatRoomEntity(String variable) {
        super(ChatRoomEntity.class, forVariable(variable));
    }

    public QChatRoomEntity(Path<? extends ChatRoomEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoomEntity(PathMetadata metadata) {
        super(ChatRoomEntity.class, metadata);
    }

}

