package com.chapter02;

import com.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 第2节课：纯注解方式 Mapper 接口（老师讲评示例，按老师 AnnotationTest 反推重建）
 *
 * 和我自己的 UserMapper 接口的区别：
 *   · 我的 UserMapper：XML 6 个方法 + 注解 6 个方法混在一个接口里（靠 Anno 后缀区分）
 *   · 老师的 UserMapperAnnotation：接口只放注解方法，XML 方法全在另一个 UserMapper 里
 * 两种组织方式都对，实际项目里【一个接口配一个 XML】分得更开是主流做法。
 *
 * 对应测试：src/test/java/com/chapter02/UserMapperAnnotationTest.java
 */
public interface UserMapperAnnotation {

    /**
     * 查询所有用户（resultType 默认按 列名=属性名 映射）
     */
    @Select("SELECT * FROM user")
    List<User> findAll();

    /**
     * 根据 ID 查询用户
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);

    /**
     * 新增用户 + 自增主键回填（@Options = XML 的 useGeneratedKeys/keyProperty）
     */
    @Insert("INSERT INTO user(username, password, email) VALUES(#{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);
}
