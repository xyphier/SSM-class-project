-- ============================================
-- Mybatis 实验数据库初始化脚本
-- 数据库：mybatis_db    表：user
-- ============================================

-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS mybatis_db DEFAULT CHARACTER SET utf8mb4;
USE mybatis_db;

-- 2. 创建用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`       INT          NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(255) NOT NULL                COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL                COMMENT '密码',
  `email`    VARCHAR(255) NOT NULL                COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. 插入测试数据（与实验预期输出一致）
INSERT INTO `user` (`username`, `password`, `email`) VALUES
('张三', '123', 'zhangsan@qq.com'),
('李四', '456', 'lisi@qq.com'),
('王五', '789', 'wangwu@qq.com');
