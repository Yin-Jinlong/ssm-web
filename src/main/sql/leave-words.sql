create table leave_words
(
    id   int auto_increment
        primary key,
    uid  int unsigned                 not null,
    msg  varchar(128)                 not null comment '留言',
    time datetime default (curtime()) not null comment '时间',
    constraint leave_words_user_uid_fk
        foreign key (uid) references user (uid)
)
    comment '留言';

INSERT INTO ssm_web.leave_words (id, uid, msg, time) VALUES (1, 732418, '测试消息', '2023-09-13 17:31:37');
INSERT INTO ssm_web.leave_words (id, uid, msg, time) VALUES (2, 732419, '佛系摆烂', '2023-09-13 17:32:02');