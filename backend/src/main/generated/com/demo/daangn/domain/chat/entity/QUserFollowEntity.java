package com.demo.daangn.domain.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFollowEntity is a Querydsl query type for UserFollowEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserFollowEntity extends EntityPathBase<UserFollowEntity> {

    private static final long serialVersionUID = -1945589665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFollowEntity userFollowEntity = new QUserFollowEntity("userFollowEntity");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final com.demo.daangn.domain.user.entity.QDaangnUserEntity folloer;

    public final com.demo.daangn.domain.user.entity.QDaangnUserEntity follow;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QUserFollowEntity(String variable) {
        this(UserFollowEntity.class, forVariable(variable), INITS);
    }

    public QUserFollowEntity(Path<? extends UserFollowEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFollowEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFollowEntity(PathMetadata metadata, PathInits inits) {
        this(UserFollowEntity.class, metadata, inits);
    }

    public QUserFollowEntity(Class<? extends UserFollowEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.folloer = inits.isInitialized("folloer") ? new com.demo.daangn.domain.user.entity.QDaangnUserEntity(forProperty("folloer")) : null;
        this.follow = inits.isInitialized("follow") ? new com.demo.daangn.domain.user.entity.QDaangnUserEntity(forProperty("follow")) : null;
    }

}

