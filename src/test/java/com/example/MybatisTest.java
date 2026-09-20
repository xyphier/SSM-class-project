package com.example;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Mybatis 测试类
 * 每个功能两个版本：XML 实现 / 注解实现
 * 右键方法名 -> Run 'testXxx()' 即可单独运行某个测试
 */
public class MybatisTest {

    /** 工具方法：打开 SqlSession */
    private SqlSession openSession() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        return factory.openSession();
    }

    /** 工具方法：构造一个测试用户 */
    private User newUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

    // ==================== XML 方式 ====================

    @Test
    public void testXmlFindAll() throws IOException {
        System.out.println("============ [XML] 查询全部 ============");
        SqlSession session = openSession();
        List<User> users = session.getMapper(UserMapper.class).findAll();
        users.forEach(System.out::println);
        session.close();
    }

    @Test
    public void testXmlFindById() throws IOException {
        System.out.println("============ [XML] 按ID查询 ============");
        SqlSession session = openSession();
        User user = session.getMapper(UserMapper.class).findById(1);
        System.out.println(user);
        session.close();
    }

    @Test
    public void testXmlInsert() throws IOException {
        System.out.println("============ [XML] 新增 + 自增主键回填 ============");
        SqlSession session = openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        User user = newUser("xml_insert_test", "123456", "xml@test.com");
        System.out.println("插入前 id = " + user.getId());   // null

        int rows = mapper.insert(user);
        session.commit();

        System.out.println("影响行数 = " + rows);
        System.out.println("插入后 id = " + user.getId());   // 已回填自增主键

        // 清理测试数据
        mapper.delete(user.getId());
        session.commit();
        session.close();
    }

    @Test
    public void testXmlUpdate() throws IOException {
        System.out.println("============ [XML] 修改 ============");
        SqlSession session = openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        // 先插入一条临时数据用于修改
        User user = newUser("xml_update_test", "111", "old@test.com");
        mapper.insert(user);
        session.commit();

        user.setEmail("new@test.com");
        int rows = mapper.update(user);
        session.commit();
        System.out.println("影响行数 = " + rows);
        System.out.println("修改后 = " + mapper.findById(user.getId()));

        // 清理测试数据
        mapper.delete(user.getId());
        session.commit();
        session.close();
    }

    @Test
    public void testXmlDelete() throws IOException {
        System.out.println("============ [XML] 删除 ============");
        SqlSession session = openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        // 先插入一条临时数据用于删除
        User user = newUser("xml_delete_test", "123456", "del@test.com");
        mapper.insert(user);
        session.commit();
        System.out.println("删除前存在: " + (mapper.findById(user.getId()) != null));

        int rows = mapper.delete(user.getId());
        session.commit();
        System.out.println("影响行数 = " + rows);
        System.out.println("删除后存在: " + (mapper.findById(user.getId()) != null));
        session.close();
    }

    @Test
    public void testXmlFindByNameAndPassword() throws IOException {
        System.out.println("============ [XML] 模糊查询（多参 @Param）============");
        SqlSession session = openSession();
        // 参数名 username/password 由接口方法上的 @Param 指定
        List<User> users = session.getMapper(UserMapper.class)
                .findByNameAndPassword("li", "123");
        System.out.println("用户名含 li 且 密码含 123 的用户: " + users);
        session.close();
    }

    // ==================== 注解方式 ====================

    @Test
    public void testAnnoFindAll() throws IOException {
        System.out.println("============ [注解] 查询全部 ============");
        SqlSession session = openSession();
        List<User> users = session.getMapper(UserMapper.class).findAllAnno();
        users.forEach(System.out::println);
        session.close();
    }

    @Test
    public void testAnnoFindById() throws IOException {
        System.out.println("============ [注解] 按ID查询 ============");
        SqlSession session = openSession();
        User user = session.getMapper(UserMapper.class).findByIdAnno(1);
        System.out.println(user);
        session.close();
    }

    @Test
    public void testAnnoInsert() throws IOException {
        System.out.println("============ [注解] 新增 + 自增主键回填 ============");
        SqlSession session = openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        User user = newUser("anno_insert_test", "123456", "anno@test.com");
        System.out.println("插入前 id = " + user.getId());   // null

        int rows = mapper.insertAnno(user);
        session.commit();

        System.out.println("影响行数 = " + rows);
        System.out.println("插入后 id = " + user.getId());   // @Options 回填自增主键

        // 清理测试数据
        mapper.deleteAnno(user.getId());
        session.commit();
        session.close();
    }

    @Test
    public void testAnnoUpdate() throws IOException {
        System.out.println("============ [注解] 修改 ============");
        SqlSession session = openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        User user = newUser("anno_update_test", "111", "old@test.com");
        mapper.insertAnno(user);
        session.commit();

        user.setEmail("new@test.com");
        int rows = mapper.updateAnno(user);
        session.commit();
        System.out.println("影响行数 = " + rows);
        System.out.println("修改后 = " + mapper.findByIdAnno(user.getId()));

        // 清理测试数据
        mapper.deleteAnno(user.getId());
        session.commit();
        session.close();
    }

    @Test
    public void testAnnoDelete() throws IOException {
        System.out.println("============ [注解] 删除 ============");
        SqlSession session = openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);

        User user = newUser("anno_delete_test", "123456", "del@test.com");
        mapper.insertAnno(user);
        session.commit();
        System.out.println("删除前存在: " + (mapper.findByIdAnno(user.getId()) != null));

        int rows = mapper.deleteAnno(user.getId());
        session.commit();
        System.out.println("影响行数 = " + rows);
        System.out.println("删除后存在: " + (mapper.findByIdAnno(user.getId()) != null));
        session.close();
    }

    @Test
    public void testAnnoFindByNameAndPassword() throws IOException {
        System.out.println("============ [注解] 模糊查询（多参 @Param）============");
        SqlSession session = openSession();
        List<User> users = session.getMapper(UserMapper.class)
                .findByNameAndPasswordAnno("li", "123");
        System.out.println("用户名含 li 且 密码含 123 的用户: " + users);
        session.close();
    }
}
