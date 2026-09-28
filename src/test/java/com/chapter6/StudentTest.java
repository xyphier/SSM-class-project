package com.chapter6;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.MybatisXMLConfigBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chapter6.config.MybatisPlusConfig;
import com.chapter6.config.StudentMetaObjectHandler;
import com.chapter6.entity.Student;
import com.chapter6.mapper.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyBatis-Plus 核心功能测试。
 *
 * 第六章第一节课：BaseMapper 的新增和按 ID 查询。
 * 第六章第二节课新增效果：条件构造器、分页、自动填充、逻辑删除、
 * 乐观锁、字段策略、指定字段查询、Map 查询和分组统计。
 */
public class StudentTest {

    private SqlSession sqlSession;
    private StudentMapper studentMapper;

    @Before
    public void init() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("chapter6/mybatis-plus-config.xml");
        MybatisXMLConfigBuilder parser = new MybatisXMLConfigBuilder(inputStream);
        MybatisConfiguration configuration = (MybatisConfiguration) parser.parse();

        // 第六章第二节课新增：注册分页、防全表操作和乐观锁插件。
        configuration.addInterceptor(MybatisPlusConfig.buildInterceptor());

        GlobalConfig globalConfig = GlobalConfigUtils.getGlobalConfig(configuration);
        globalConfig.setMetaObjectHandler(new StudentMetaObjectHandler());

        SqlSessionFactory factory = new MybatisSqlSessionFactoryBuilder().build(configuration);
        sqlSession = factory.openSession(true);
        studentMapper = sqlSession.getMapper(StudentMapper.class);
    }

    @After
    public void destroy() {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    // 第一节课：BaseMapper 基础 CRUD

    @Test
    public void testInsert() {
        System.out.println("========== 1-1 插入（自动填充 createTime/updateTime） ==========");
        Student student = new Student("小明", 20, "xiaoming@test.com", "计算机科学", 85.0);
        int rows = studentMapper.insert(student);
        System.out.println("影响行数：" + rows);
        System.out.println("插入后实体：" + student);
    }

    @Test
    public void testSelectById() {
        System.out.println("========== 1-2 根据 ID 查询 ==========");
        System.out.println("查询结果：" + studentMapper.selectById(1L));
    }

    // 以下为第六章第二节课新增效果

    @Test
    public void testUpdateById() {
        System.out.println("========== 2-1 根据 ID 更新 ==========");
        Student student = studentMapper.selectById(1L);
        student.setScore(99.0);
        int rows = studentMapper.updateById(student);
        System.out.println("影响行数：" + rows);
        System.out.println("更新后：" + studentMapper.selectById(1L));
    }

    @Test
    public void testDeleteById() {
        System.out.println("========== 2-2 根据 ID 逻辑删除 ==========");
        Student student = new Student("临时学生", 18, "tmp@test.com", "测试", 60.0);
        studentMapper.insert(student);
        int rows = studentMapper.deleteById(student.getId());
        System.out.println("影响行数：" + rows);
        System.out.println("删除后再查询：" + studentMapper.selectById(student.getId()));
    }

    @Test
    public void testBatchIds() {
        System.out.println("========== 3 批量查询 ==========");
        List<Student> list = studentMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        list.forEach(System.out::println);
    }

    @Test
    public void testQueryWrapperBasic() {
        System.out.println("========== 4-1 QueryWrapper 基本条件 ==========");
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("major", "软件工程")
                .gt("score", 70)
                .lt("score", 100)
                .like("name", "张")
                .orderByDesc("score");
        studentMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    public void testQueryWrapperOr() {
        System.out.println("========== 4-2 QueryWrapper 的 OR 嵌套 ==========");
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("major", "计算机科学")
                .and(w -> w.gt("score", 90).or().gt("age", 21))
                .orderByDesc("score");
        studentMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    public void testSelectCountAndOne() {
        System.out.println("========== 4-3 selectCount / selectOne ==========");
        QueryWrapper<Student> countWrapper = new QueryWrapper<>();
        countWrapper.eq("major", "计算机科学");
        System.out.println("计算机科学专业人数：" + studentMapper.selectCount(countWrapper));

        QueryWrapper<Student> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("name", "张三");
        System.out.println("姓名为张三的学生：" + studentMapper.selectOne(oneWrapper));
    }

    @Test
    public void testLambdaQueryWrapper() {
        System.out.println("========== 5 LambdaQueryWrapper ==========");
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getMajor, "计算机科学")
                .ge(Student::getScore, 80)
                .likeRight(Student::getName, "李")
                .orderByAsc(Student::getAge);
        studentMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    public void testUpdateWrapper() {
        System.out.println("========== 6-1 LambdaUpdateWrapper 条件更新 ==========");
        Student values = new Student();
        values.setScore(100.0);
        LambdaUpdateWrapper<Student> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Student::getMajor, "人工智能").lt(Student::getScore, 90);
        System.out.println("影响行数：" + studentMapper.update(values, wrapper));
    }

    @Test
    public void testUpdateByIdFieldStrategy() {
        System.out.println("========== 6-2 NOT_NULL 字段策略 ==========");
        Student student = studentMapper.selectById(2L);
        Double originalScore = student.getScore();
        student.setName("李四（改名版）");
        student.setScore(null);
        studentMapper.updateById(student);
        Student updated = studentMapper.selectById(2L);
        System.out.println("原成绩：" + originalScore + "，更新后成绩：" + updated.getScore());
    }

    @Test
    public void testPage() {
        System.out.println("========== 7 分页查询 ==========");
        Page<Student> page = new Page<>(1, 2);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Student::getScore);
        IPage<Student> result = studentMapper.selectPage(page, wrapper);
        System.out.println("总记录数：" + result.getTotal());
        System.out.println("总页数：" + result.getPages());
        result.getRecords().forEach(System.out::println);
    }

    @Test
    public void testAutoFill() {
        System.out.println("========== 8 自动填充 ==========");
        Student student = new Student("自动填充测试", 25, "auto@test.com", "自动化", 99.9);
        studentMapper.insert(student);
        System.out.println("插入后：" + studentMapper.selectById(student.getId()));
    }

    @Test
    public void testLogicDelete() {
        System.out.println("========== 9 逻辑删除 ==========");
        Student student = new Student("逻辑删除测试", 30, "logic@test.com", "测试", 50.0);
        studentMapper.insert(student);
        studentMapper.deleteById(student.getId());
        System.out.println("删除后再查询：" + studentMapper.selectById(student.getId()));
    }

    @Test
    public void testOptimisticLock() {
        System.out.println("========== 10 乐观锁 ==========");
        Student student = studentMapper.selectById(3L);
        int beforeVersion = student.getVersion();
        student.setScore(96.0);
        int successRows = studentMapper.updateById(student);
        System.out.println("正确版本更新行数：" + successRows);
        System.out.println("version：" + beforeVersion + " -> " + student.getVersion());

        Student stale = new Student();
        stale.setId(3L);
        stale.setName("过期版本更新");
        stale.setVersion(beforeVersion);
        int staleRows = studentMapper.updateById(stale);
        System.out.println("使用过期版本更新行数：" + staleRows);
    }

    @Test
    public void testSelectOnlySomeFields() {
        System.out.println("========== 11 指定字段和 Map 查询 ==========");
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Student::getId, Student::getName, Student::getScore)
                .gt(Student::getScore, 90);
        studentMapper.selectList(wrapper).forEach(System.out::println);

        QueryWrapper<Student> mapWrapper = new QueryWrapper<>();
        mapWrapper.select("name", "score", "major").eq("major", "计算机科学");
        studentMapper.selectMaps(mapWrapper).forEach(System.out::println);
    }

    @Test
    public void testGroupBy() {
        System.out.println("========== 12 分组统计 ==========");
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.select("major", "AVG(score) AS avg_score", "COUNT(*) AS count")
                .groupBy("major")
                .orderByDesc("avg_score");
        studentMapper.selectMaps(wrapper).forEach(System.out::println);
    }

    @Test
    public void testSelectByMap() {
        System.out.println("========== 13 selectByMap ==========");
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("major", "计算机科学");
        conditions.put("age", 20);
        studentMapper.selectByMap(conditions).forEach(System.out::println);
    }
}
