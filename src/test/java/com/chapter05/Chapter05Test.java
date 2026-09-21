package com.chapter05;

import com.entity.Dept;
import com.entity.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class Chapter05Test {

    private SqlSession sqlSession;
    private Chapter05EmpMapper empMapper;
    private Chapter05DeptMapper deptMapper;

    @Before
    public void init() throws Exception {
        InputStream inputStream = Resources.getResourceAsStream("chapter05/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = factory.openSession();
        empMapper = sqlSession.getMapper(Chapter05EmpMapper.class);
        deptMapper = sqlSession.getMapper(Chapter05DeptMapper.class);
    }

    @After
    public void destroy() {
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    /** 一对一：按员工编号查员工，同时显示其所属部门。 */
    @Test
    public void testOne2oneByXml() {
        Emp emp = empMapper.one2oneByXml(7369);
        System.out.println("========== 一对一：员工及其部门 ==========");
        if (emp == null) {
            System.out.println("未找到员工。");
            return;
        }
        System.out.println("员工：" + emp.getEname() + "（编号：" + emp.getEmpno() + "）");
        System.out.println("部门：" + (emp.getDept() == null ? "无" : emp.getDept().getDname()));
        System.out.println("地点：" + (emp.getDept() == null ? "无" : emp.getDept().getLoc()));
    }

    /** 一对多：按部门编号查部门，同时列出它的全部员工。 */
    @Test
    public void testOne2manyByXml() {
        Dept dept = deptMapper.one2manyByXml(20);
        System.out.println("========== 一对多：部门及其员工 ==========");
        if (dept == null) {
            System.out.println("未找到部门。");
            return;
        }
        System.out.println("部门：" + dept.getDname() + "（编号：" + dept.getDeptno() + "）");
        System.out.println("地点：" + dept.getLoc());
        System.out.println("员工数量：" + dept.getEmps().size());
        for (Emp emp : dept.getEmps()) {
            System.out.println("- " + emp.getEname() + "（" + emp.getJob() + "）");
        }
    }
}
