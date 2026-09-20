package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户 Mapper 接口
 * 6 个 CRUD 功能分别用两种方式各实现一遍：
 *   1) XML 方式：SQL 写在 resources/mapper/UserMapper.xml 中
 *   2) 注解方式：SQL 用 @Select/@Insert/@Update/@Delete 注解直接写在方法上（方法名加 Anno 后缀）
 * 注意：同一个接口里两种方式是混用的，只要方法名（即 statement id）不重复就不会互相影响；
 * 如果同名方法在 XML 和注解里都定义，MyBatis 会抛出重复定义的异常。
 */
public interface UserMapper {

    // ==================== 以下 6 个：XML 方式（SQL 见 UserMapper.xml）====================

    /**
     * 1. 查询所有用户
     */
    List<User> findAll();

    /**
     * 2. 根据 ID 查询用户
     */
    User findById(Integer id);

    /**
     * 3. 新增用户（XML 中配置 useGeneratedKeys + keyProperty 实现自增主键回填）
     */
    int insert(User user);

    /**
     * 4. 修改用户
     */
    int update(User user);

    /**
     * 5. 删除用户
     */
    int delete(Integer id);

    /**
     * 6. 模糊查询（多参示例）
     * 方法有多个参数时，必须用 @Param 给每个参数命名，
     * XML 中的 SQL 才能用 #{参数名} 按名字取值。
     */
    List<User> findByNameAndPassword(@Param("username") String username,
                                     @Param("password") String password);

    // ==================== 以下 6 个：注解方式（SQL 直接写在注解里）====================

    /**
     * 1. 查询所有用户
     */
    @Select("SELECT * FROM user")
    List<User> findAllAnno();

    /**
     * 2. 根据 ID 查询用户
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findByIdAnno(Integer id);

    /**
     * 3. 新增用户 + 自增主键回填
     * @Options(useGeneratedKeys/keyProperty) 等价于 XML insert 标签上的两个同名属性
     */
    @Insert("INSERT INTO user(username, password, email) VALUES(#{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAnno(User user);

    /**
     * 4. 修改用户
     */
    @Update("UPDATE user SET username = #{username}, password = #{password}, email = #{email} WHERE id = #{id}")
    int updateAnno(User user);

    /**
     * 5. 删除用户
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteAnno(Integer id);

    /**
     * 6. 模糊查询（注解版多参示例，注解 SQL 同样靠 @Param 按名字引用参数）
     */
    @Select("SELECT * FROM user " +
            "WHERE username LIKE CONCAT('%', #{username}, '%') " +
            "AND password LIKE CONCAT('%', #{password}, '%')")
    List<User> findByNameAndPasswordAnno(@Param("username") String username,
                                         @Param("password") String password);
}
