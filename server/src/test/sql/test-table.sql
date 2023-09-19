create table test
(
    name char(16) not null
        primary key,
    age  int      null
);

INSERT INTO ssm_web.test (name, age)
VALUES ('小明', 18);