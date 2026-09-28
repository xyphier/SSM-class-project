package com.chapter05;

import com.entity.Dept;
import com.entity.Emp;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/** Chapter 5：第二节课新增部门侧的一对多注解关联查询。 */
public interface Chapter05DeptMapper {

    Dept one2manyByXml(@Param("deptno") Integer deptno);

    // 第二节课新增：@Many 根据部门编号查询员工集合。
    @Select("SELECT deptno, dname, loc FROM dept WHERE deptno = #{deptno}")
    @Results({
            @Result(property = "deptno", column = "deptno", id = true),
            @Result(property = "emps", column = "deptno",
                    many = @Many(select = "com.chapter05.Chapter05DeptMapper.selectEmpsByDeptno"))
    })
    Dept one2manyByAnn(@Param("deptno") Integer deptno);

    @Select("SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno " +
            "FROM emp WHERE deptno = #{deptno} ORDER BY empno")
    List<Emp> selectEmpsByDeptno(@Param("deptno") Integer deptno);
}
