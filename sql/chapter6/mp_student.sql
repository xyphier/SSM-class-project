-- Chapter 6：MyBatis-Plus 学生表和初始数据。
-- 第六章第二节课新增效果：分页、逻辑删除、乐观锁和条件构造器测试数据。
-- 脚本不会删除或覆盖已有数据，可重复执行。
USE mybatis_db;

CREATE TABLE IF NOT EXISTS mp_student (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '学生编号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    age INT COMMENT '年龄',
    email VARCHAR(100) COMMENT '邮箱',
    major VARCHAR(100) COMMENT '专业',
    score DOUBLE COMMENT '成绩',
    version INT NOT NULL DEFAULT 1 COMMENT '乐观锁版本号',
    deleted INT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0正常，1删除',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO mp_student
    (id, name, age, email, major, score, version, deleted, create_time, update_time)
VALUES
    (1, '张三', 19, 'zhangsan@test.com', '软件工程', 88.5, 1, 0, NOW(), NOW()),
    (2, '李四', 20, 'lisi@test.com', '计算机科学', 92.0, 1, 0, NOW(), NOW()),
    (3, '王五', 21, 'wangwu@test.com', '人工智能', 86.0, 1, 0, NOW(), NOW())
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    age = VALUES(age),
    email = VALUES(email),
    major = VALUES(major),
    score = VALUES(score),
    version = VALUES(version),
    deleted = VALUES(deleted),
    update_time = NOW();
