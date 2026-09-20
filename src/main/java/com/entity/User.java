package com.entity;

import java.util.Date;

/**
 * 用户实体类（全项目共享，chapter02 / chapter03 都用它）
 * 对应数据库表：user
 *
 * 第3节课起补充了 createTime / updateTime 字段，
 * 数据库列名是 create_time / update_time（下划线风格），
 * 与属性名 createTime（驼峰风格）不一致 —— 这正是第3节课
 * resultType 映射失败、必须改用 resultMap 的原因。
 */
public class User {
    // 字段（对应数据库表的列）
    private Integer id;           // 用户ID
    private String username;      // 用户名
    private String password;      // 密码
    private String email;         // 邮箱
    private Date createTime;      // 创建时间（列：create_time）
    private Date updateTime;      // 修改时间（列：update_time）

    // 构造方法
    public User() {
    }

    // getter 和 setter 方法（必须有，否则 Mybatis 无法封装数据）
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    // toString 方法（方便调试）
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
