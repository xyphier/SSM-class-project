package com.chapter02;

import com.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 第2节课演示入口 - MyBatis 各种查询用法
 * 可以直接右键 -> Run 'Main.main()' 运行
 */
public class Main {

    // 全局复用的 SqlSessionFactory
    private static SqlSessionFactory sqlSessionFactory;

    /**
     * 初始化 SqlSessionFactory（只加载一次配置）
     */
    private static void initFactory() throws IOException {
        InputStream is = Resources.getResourceAsStream("chapter02/mybatis-config.xml");
        if (is == null) {
            throw new IOException("找不到 mybatis-config.xml，请检查 resources 目录");
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        is.close();
    }

    public static void main(String[] args) {
        System.out.println("=== MyBatis 演示程序启动 ===\n");

        try {
            // 初始化一次 SqlSessionFactory
            initFactory();

            // 演示1：查询所有用户
            testFindAll();

            // 演示2：根据 ID 查询用户
            testFindById();

            // 演示3：多参模糊查询用户（@Param 命名参数）
            testFindByNameAndPassword();

            System.out.println("\n=== 程序运行结束 ===");

        } catch (IOException e) {
            System.err.println("运行出错：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 演示1：查询所有用户（SELECT * FROM user）
     */
    public static void testFindAll() {
        System.out.println("============ 测试查询所有用户 ============");

        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<User> users = userMapper.findAll();

            System.out.println("查询到 " + users.size() + " 条用户数据：\n");
            for (User user : users) {
                System.out.println(user);
            }
        } finally {
            session.close();
        }
        System.out.println();
    }

    /**
     * 演示2：根据 ID 查询单个用户
     */
    public static void testFindById() {
        System.out.println("============ 测试根据ID查询用户 ============");

        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.findById(1);

            if (user != null) {
                System.out.println("ID=1 的用户：" + user);
            } else {
                System.out.println("ID=1 的用户不存在");
            }
        } finally {
            session.close();
        }
        System.out.println();
    }

    /**
     * 演示3：多参模糊查询用户（@Param 命名参数）
     * 对应 XML：WHERE username LIKE CONCAT('%',#{username},'%') AND password LIKE ...
     * 同时调用注解版本，验证两种方式互不影响
     */
    public static void testFindByNameAndPassword() {
        System.out.println("============ 测试多参模糊查询（@Param） ============");

        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // 两个参数通过 @Param("username") / @Param("password") 命名
            List<User> users = userMapper.findByNameAndPassword("li", "123");
            System.out.println("XML版 模糊查询结果（用户名含 li 且 密码含 123）：");
            System.out.println("共 " + users.size() + " 条");
            for (User user : users) {
                System.out.println(user);
            }

            List<User> usersAnno = userMapper.findByNameAndPasswordAnno("li", "123");
            System.out.println("注解版 模糊查询结果：共 " + usersAnno.size() + " 条");
        } finally {
            session.close();
        }
        System.out.println();
    }
}
