package com.demo.daangn.domain.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatMessageEntity is a Querydsl query type for ChatMessageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatMessageEntity extends EntityPathBase<ChatMessageEntity> {

    private static final long serialVersionUID = -1447297966L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatMessageEntity chatMessageEntity = new QChatMessageEntity("chatMessageEntity");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isUsed = createNumber("isUsed", Integer.class);

    public final NumberPath<Integer> readed = createNumber("readed", Integer.class);

    public final QChatRoomEntity room;

    public final com.demo.daangn.domain.user.entity.QDaangnUserEntity sender;

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QChatMessageEntity(String variable) {
        this(ChatMessageEntity.class, forVariable(variable), INITS);
    }

    public QChatMessageEntity(Path<? extends ChatMessageEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatMessageEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatMessageEntity(PathMetadata metadata, PathInits inits) {
        this(ChatMessageEntity.class, metadata, inits);
    }

    public QChatMessageEntity(Class<? extends ChatMessageEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QChatRoomEntity(forProperty("room")) : null;
        this.sender = inits.isInitialized("sender") ? new com.demo.daangn.domain.user.entity.QDaangnUserEntity(forProperty("sender")) : null;
    }

}

