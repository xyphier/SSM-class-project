-- ============================================
-- Mybatis 实验数据库初始化脚本
-- 数据库：mybatis_db    表：user
-- 第3节课更新：新增 create_time / update_time 两列（下划线列名，
--              配合 chapter03 的 resultMap / 别名 映射演示）
-- ⚠ 已有数据的旧库不要跑本脚本（会 DROP TABLE），改用 upgrade_chapter03.sql 原地升级
-- ============================================

-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS mybatis_db DEFAULT CHARACTER SET utf8mb4;
USE mybatis_db;

-- 2. 创建用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username`    VARCHAR(255) NOT NULL                COMMENT '用户名',
  `password`    VARCHAR(255) NOT NULL                COMMENT '密码',
  `email`       VARCHAR(255) NOT NULL                COMMENT '邮箱',
  `create_time` DATETIME     DEFAULT NULL            COMMENT '创建时间',
  `update_time` DATETIME     DEFAULT NULL            COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. 插入测试数据
--    注意：第1条必须是 admin（老师 chapter03 的测试用例按 username='admin'、email='admin@qq.com' 查询）
--    其余三条与你现有库里的数据保持一致（zhangsan / lisi / wangwu）
INSERT INTO `user` (`username`, `password`, `email`, `create_time`, `update_time`) VALUES
('admin',    '123456', 'admin@qq.com',         NOW(), NOW()),
('zhangsan', '123456', 'zhangsan@example.com', NOW(), NOW()),
('lisi',     'abcdef', 'lisi@example.com',     NOW(), NOW()),
('wangwu',   '123456', 'wangwu@example.com',   NOW(), NOW());
