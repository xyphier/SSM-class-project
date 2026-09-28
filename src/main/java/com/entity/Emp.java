package com.entity;

import java.util.Date;
import java.util.List;

/**
 * 员工实体，对应 emp 表。
 * 多名员工可以属于同一个部门。
 */
public class Emp {

    private Integer empno;
    private String ename;
    private String job;
    private Integer mgr;
    private Date hiredate;
    private Double sal;
    private Double comm;
    private Integer deptno;
    private Dept dept;
    private List<Skill> skills;

    public Integer getEmpno() { return empno; }
    public void setEmpno(Integer empno) { this.empno = empno; }
    public String getEname() { return ename; }
    public void setEname(String ename) { this.ename = ename; }
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }
    public Integer getMgr() { return mgr; }
    public void setMgr(Integer mgr) { this.mgr = mgr; }
    public Date getHiredate() { return hiredate; }
    public void setHiredate(Date hiredate) { this.hiredate = hiredate; }
    public Double getSal() { return sal; }
    public void setSal(Double sal) { this.sal = sal; }
    public Double getComm() { return comm; }
    public void setComm(Double comm) { this.comm = comm; }
    public Integer getDeptno() { return deptno; }
    public void setDeptno(Integer deptno) { this.deptno = deptno; }
    public Dept getDept() { return dept; }
    public void setDept(Dept dept) { this.dept = dept; }
    public List<Skill> getSkills() { return skills; }
    public void setSkills(List<Skill> skills) { this.skills = skills; }
}
