create database if not exists ssm_web;
use ssm_web;

create table if not exists user
(
    uid  int unsigned default ((uuid_short() % 1000000)) not null comment '用户id' primary key,
    name char(24)                                        not null comment '用户名',
    pwd  varchar(128)                                    null comment '密码hash',
    constraint user_pk2 unique (name)
)
    comment '用户';

create table if not exists leave_words
(
    id   int auto_increment primary key,
    uid  int unsigned                 not null,
    msg  varchar(128)                 not null comment '留言',
    time datetime default (curtime()) not null comment '时间',
    constraint leave_words_user_uid_fk foreign key (uid) references user (uid)
)
    comment '留言';