package com.demo.daangn.domain.notification.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotificationEntity is a Querydsl query type for NotificationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotificationEntity extends EntityPathBase<NotificationEntity> {

    private static final long serialVersionUID = -1012771967L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotificationEntity notificationEntity = new QNotificationEntity("notificationEntity");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isReaded = createNumber("isReaded", Integer.class);

    public final NumberPath<Integer> isUsed = createNumber("isUsed", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> objectId = createNumber("objectId", Long.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final com.demo.daangn.domain.user.entity.QDaangnUserEntity user;

    public QNotificationEntity(String variable) {
        this(NotificationEntity.class, forVariable(variable), INITS);
    }

    public QNotificationEntity(Path<? extends NotificationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotificationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotificationEntity(PathMetadata metadata, PathInits inits) {
        this(NotificationEntity.class, metadata, inits);
    }

    public QNotificationEntity(Class<? extends NotificationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.demo.daangn.domain.user.entity.QDaangnUserEntity(forProperty("user")) : null;
    }

}

