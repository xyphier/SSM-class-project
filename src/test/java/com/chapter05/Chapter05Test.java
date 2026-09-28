package com.chapter05;

import com.entity.Dept;
import com.entity.Emp;
import com.entity.Skill;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

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

    /** 第二节课新增：一对一注解版，@One 根据员工的 deptno 再查询部门。 */
    @Test
    public void testOne2oneByAnn() {
        Emp emp = empMapper.one2oneByAnn(7499);
        System.out.println("========== 一对一（注解方式） ==========");
        if (emp != null) {
            System.out.println("员工：" + emp.getEname());
            System.out.println("部门：" + (emp.getDept() == null ? "无" : emp.getDept().getDname()));
        }
    }

    /** 第二节课新增：查询所有员工，每名员工都带有所属部门。 */
    @Test
    public void testMany2oneByXml() {
        List<Emp> emps = empMapper.many2oneByXml();
        System.out.println("========== 多对一（XML 方式） ==========");
        for (Emp emp : emps) {
            String deptName = emp.getDept() == null ? "无" : emp.getDept().getDname();
            System.out.println(emp.getEmpno() + " - " + emp.getEname() + " -> " + deptName);
        }
    }

    /** 第二节课新增：多对一的注解写法。 */
    @Test
    public void testMany2oneByAnn() {
        List<Emp> emps = empMapper.many2oneByAnn();
        System.out.println("========== 多对一（注解方式） ==========");
        for (Emp emp : emps) {
            String deptName = emp.getDept() == null ? "无" : emp.getDept().getDname();
            System.out.println(emp.getEmpno() + " - " + emp.getEname() + " -> " + deptName);
        }
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

    /** 第二节课新增：一对多注解版，@Many 根据部门编号查询员工集合。 */
    @Test
    public void testOne2manyByAnn() {
        Dept dept = deptMapper.one2manyByAnn(30);
        System.out.println("========== 一对多（注解方式） ==========");
        if (dept != null) {
            System.out.println("部门：" + dept.getDname());
            for (Emp emp : dept.getEmps()) {
                System.out.println("- " + emp.getEname() + "（" + emp.getJob() + "）");
            }
        }
    }

    /** 第二节课新增：通过 emp_skill 中间表查询员工的全部技能。 */
    @Test
    public void testMany2manyByXml() {
        Emp emp = empMapper.many2manyByXml(7566);
        System.out.println("========== 多对多（XML 方式） ==========");
        printEmployeeSkills(emp);
    }

    /** 第二节课新增：使用 @Many 查询员工的技能集合。 */
    @Test
    public void testMany2manyByAnn() {
        Emp emp = empMapper.many2manyByAnn(7788);
        System.out.println("========== 多对多（注解方式） ==========");
        printEmployeeSkills(emp);
    }

    private void printEmployeeSkills(Emp emp) {
        if (emp == null) {
            System.out.println("未找到员工。");
            return;
        }
        System.out.println("员工：" + emp.getEname() + "（编号：" + emp.getEmpno() + "）");
        int count = emp.getSkills() == null ? 0 : emp.getSkills().size();
        System.out.println("技能数量：" + count);
        if (emp.getSkills() != null) {
            for (Skill skill : emp.getSkills()) {
                System.out.println("- " + skill.getName() + "：" + skill.getDescription());
            }
        }
    }
}
