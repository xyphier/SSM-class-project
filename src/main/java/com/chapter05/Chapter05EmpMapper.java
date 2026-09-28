package com.chapter05;

import com.entity.Dept;
import com.entity.Emp;
import com.entity.Skill;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/** Chapter 5：第二节课新增员工侧的一对一、多对一和多对多关联查询。 */
public interface Chapter05EmpMapper {

    // XML 方式
    Emp one2oneByXml(@Param("empno") Integer empno);
    List<Emp> many2oneByXml();
    Emp many2manyByXml(@Param("empno") Integer empno);

    // 第二节课新增：注解方式，先查员工，再用 @One 查询其部门。
    @Select("SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp WHERE empno = #{empno}")
    @Results(id = "empWithDeptAnnMap", value = {
            @Result(property = "empno", column = "empno", id = true),
            @Result(property = "deptno", column = "deptno"),
            @Result(property = "dept", column = "deptno",
                    one = @One(select = "com.chapter05.Chapter05EmpMapper.selectDeptById"))
    })
    Emp one2oneByAnn(@Param("empno") Integer empno);

    @Select("SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp ORDER BY empno")
    @Results({
            @Result(property = "empno", column = "empno", id = true),
            @Result(property = "deptno", column = "deptno"),
            @Result(property = "dept", column = "deptno",
                    one = @One(select = "com.chapter05.Chapter05EmpMapper.selectDeptById"))
    })
    List<Emp> many2oneByAnn();

    @Select("SELECT deptno, dname, loc FROM dept WHERE deptno = #{deptno}")
    Dept selectDeptById(@Param("deptno") Integer deptno);

    // 第二节课新增：用 @Many 把中间表查出的多个技能装入 skills 集合。
    @Select("SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp WHERE empno = #{empno}")
    @Results({
            @Result(property = "empno", column = "empno", id = true),
            @Result(property = "skills", column = "empno",
                    many = @Many(select = "com.chapter05.Chapter05EmpMapper.selectSkillsByEmpno"))
    })
    Emp many2manyByAnn(@Param("empno") Integer empno);

    @Select("SELECT s.id, s.name, s.description " +
            "FROM skill s INNER JOIN emp_skill es ON s.id = es.skill_id " +
            "WHERE es.empno = #{empno} ORDER BY s.id")
    List<Skill> selectSkillsByEmpno(@Param("empno") Integer empno);
}
