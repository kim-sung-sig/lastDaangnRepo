-- 1. user & chat

drop table if exists notifications;
drop table if exists chat_Message;
drop table if exists chat_Room_User;
drop table if exists chat_Room;
drop table if exists user_follow;
drop table if exists my_users;


CREATE TABLE my_users (
    id bigint PRIMARY key AUTO_INCREMENT,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    role varchar(20) NOT NULL,
    nick_name varchar(50) NOT NULL UNIQUE,
    user_profile varchar(255) DEFAULT NULL,
    isUsed tinyint(1) DEFAULT 1,
    createDate timestamp DEFAULT CURRENT_TIMESTAMP,
    modifiedDate timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE chat_room (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    chat_room_cd varchar(64) UNIQUE,
    createDate timestamp DEFAULT CURRENT_TIMESTAMP,
    modifiedDate timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE chat_room_user (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    user_Id bigint NOT NULL,
    chatRoom_Id bigint NOT NULL,
    chatRorm_name varchar(255) DEFAULT NULL,
    pointer bigint DEFAULT 0,
    is_used tinyint(1) DEFAULT 1,
    createDate timestamp DEFAULT CURRENT_TIMESTAMP,
    modifiedDate timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `FKa7w2jl4suqo9w1kdoxx05gqad` FOREIGN KEY (chatRoom_Id) REFERENCES chat_room (id),
    CONSTRAINT `FKouwfit7m8dej5xl5b3yrkeeqv` FOREIGN KEY (user_Id) REFERENCES my_users (id)
);

CREATE TABLE chat_message (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    types int NOT NULL,
    roomId bigint NOT NULL,
    sender bigint NOT NULL,
    is_used tinyint(1) DEFAULT 1,
    readed int NOT NULL,
    content varchar(400) NOT NULL,
    createDate timestamp DEFAULT CURRENT_TIMESTAMP,
    modifiedDate timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `FK2p19hry0blrv0frwcy3n650et` FOREIGN KEY (sender) REFERENCES my_users (id),
    CONSTRAINT `FK3w13lp8tgyj93dp7gpry5qp3m` FOREIGN KEY (roomId) REFERENCES chat_room (id)
);

CREATE TABLE notifications (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    types int NOT NULL,
    user_id bigint NOT NULL,
    is_readed tinyint(1) DEFAULT 1,
    is_used tinyint(1) DEFAULT 1,
    create_date timestamp DEFAULT CURRENT_TIMESTAMP,
    modifed_date timestamp DEFAULT CURRENT_TIMESTAMP,
    object_id bigint DEFAULT NULL,
    CONSTRAINT `FKkotomwqbglu6xq158eyuiqtsj` FOREIGN KEY (user_id) REFERENCES my_users (id)
);


insert into my_users (username, password, role, nick_name)
values ('test1', '1234', 'ROLE_ADMIN', '테스트계정1');

insert into my_users (username, password, role, nick_name)
values ('test2', '1234', 'ROLE_ADMIN', '테스트계정2');