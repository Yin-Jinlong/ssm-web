create database if not exists ssm_web_test;
use ssm_web_test;

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


truncate leave_words;
delete from user;

lock tables user write ,leave_words write;
# 默认用户 user,test 密码 123456
insert into user
values (100001, 'user',
        'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413'),
       (100002, 'test',
        'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413');

insert into leave_words
values (1, 100002, '测试消息', default),
       (2, 100001, 'hello world', default);

unlock tables;