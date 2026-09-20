-- ============================================
-- 第3节课数据库升级脚本（老库专用）
-- 如果你的 mybatis_db.user 表还是第2节课的旧结构（没有 create_time/update_time），
-- 不要重跑 user_db.sql（会 DROP 表清数据），执行本文件即可原地升级。
-- ============================================
USE mybatis_db;

-- 1. 补两列（已存在会报错 Duplicate column name，属正常，跳过对应语句即可）
ALTER TABLE `user` ADD COLUMN `create_time` DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE `user` ADD COLUMN `update_time` DATETIME DEFAULT NULL COMMENT '修改时间';

-- 2. 存量数据的时间字段填上当前时间
UPDATE `user` SET create_time = NOW(), update_time = NOW() WHERE create_time IS NULL;

-- 3. 确保 admin 用户存在（老师 chapter03 测试用例依赖 username='admin'）
--    其余 zhangsan/lisi/wangwu 是你库里已有的数据，保持不动
INSERT INTO `user` (`username`, `password`, `email`, `create_time`, `update_time`)
SELECT 'admin', '123456', 'admin@qq.com', NOW(), NOW()
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `user` WHERE `username` = 'admin');

-- 4. 验证
SELECT * FROM `user`;
