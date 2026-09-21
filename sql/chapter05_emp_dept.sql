-- Chapter 05：员工与部门关联查询所需的示例数据。
-- 在 mybatis_db 数据库中执行；脚本不会删除已有数据。
USE mybatis_db;

CREATE TABLE IF NOT EXISTS dept (
    deptno INT PRIMARY KEY,
    dname VARCHAR(30) NOT NULL,
    loc VARCHAR(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS emp (
    empno INT PRIMARY KEY,
    ename VARCHAR(30) NOT NULL,
    job VARCHAR(30),
    mgr INT,
    hiredate DATE,
    sal DOUBLE,
    comm DOUBLE,
    deptno INT,
    CONSTRAINT fk_emp_dept FOREIGN KEY (deptno) REFERENCES dept(deptno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO dept (deptno, dname, loc) VALUES
    (10, 'ACCOUNTING', 'NEW YORK'),
    (20, 'RESEARCH', 'DALLAS'),
    (30, 'SALES', 'CHICAGO'),
    (40, 'OPERATIONS', 'BOSTON');

INSERT IGNORE INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno) VALUES
    (7369, 'SMITH', 'CLERK', 7902, '1980-12-17', 800, NULL, 20),
    (7566, 'JONES', 'MANAGER', 7839, '1981-04-02', 2975, NULL, 20),
    (7788, 'SCOTT', 'ANALYST', 7566, '1982-12-09', 3000, NULL, 20),
    (7876, 'ADAMS', 'CLERK', 7788, '1983-01-12', 1100, NULL, 20),
    (7499, 'ALLEN', 'SALESMAN', 7698, '1981-02-20', 1600, 300, 30);
