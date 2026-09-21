package com.chapter05;

import com.entity.Dept;
import org.apache.ibatis.annotations.Param;

/** XML 方式：按部门编号查询部门及其全部员工。 */
public interface Chapter05DeptMapper {

    Dept one2manyByXml(@Param("deptno") Integer deptno);
}
