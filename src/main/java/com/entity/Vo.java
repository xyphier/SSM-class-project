package com.entity;

/**
 * 视图对象（View Object）—— 第3节课老师讲评用
 *
 * 作用：演示 resultType 并不要求映射到"实体类"，
 * 只要【查询结果列名 = 类属性名】，任何类都能被封装。
 * 比如 selectAllByVo 只查 username、email 两列 → 封装进本类，
 * 表里的其他列（id/password/时间）根本不需要出现在 Vo 里。
 *
 * 常见用途：多表联查结果、报表数据、只给前端看的字段子集。
 */
public class Vo {
    private String username;
    private String email;

    public Vo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Vo{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
