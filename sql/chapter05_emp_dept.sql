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

-- 第二节课新增效果：员工与技能是多对多关系，emp_skill 保存双方主键。
CREATE TABLE IF NOT EXISTS skill (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS emp_skill (
    empno INT NOT NULL,
    skill_id INT NOT NULL,
    PRIMARY KEY (empno, skill_id),
    CONSTRAINT fk_emp_skill_emp FOREIGN KEY (empno) REFERENCES emp(empno),
    CONSTRAINT fk_emp_skill_skill FOREIGN KEY (skill_id) REFERENCES skill(id)
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

INSERT IGNORE INTO skill (id, name, description) VALUES
    (1, 'Java', 'Java 基础与面向对象编程'),
    (2, 'MySQL', '关系型数据库设计与 SQL'),
    (3, 'MyBatis', 'MyBatis 持久层框架'),
    (4, 'Git', '代码版本管理');

INSERT IGNORE INTO emp_skill (empno, skill_id) VALUES
    (7369, 2),
    (7566, 1),
    (7566, 2),
    (7788, 1),
    (7788, 3),
    (7788, 4);
