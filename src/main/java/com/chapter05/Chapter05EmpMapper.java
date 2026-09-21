package com.chapter05;

import com.entity.Emp;
import org.apache.ibatis.annotations.Param;

/** XML 方式：按员工编号查询员工及其所属部门。 */
public interface Chapter05EmpMapper {

    Emp one2oneByXml(@Param("empno") Integer empno);
}
