create table user
(
    uid  int unsigned default ((uuid_short() % 1000000)) not null comment '用户id'
        primary key,
    name char(24)                                        not null comment '用户名',
    pwd  varchar(128)                                    null comment '密码hash',
    constraint user_pk2
        unique (name)
)
    comment '用户';

# 密码 123456
insert into user
values (732418, 'user',
        'ba3253876aed6bc22d4a6ff53d8406c6ad864195ed144ab5c87621b6c233b548baeae6956df346ec8c17f5ea10f35ee3cbc514797ed7ddd3145464e2a0bab413')