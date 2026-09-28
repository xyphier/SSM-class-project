package com.chapter6.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chapter6.entity.Student;

/**
 * 继承 BaseMapper 后，MyBatis-Plus 会自动提供常用的增删改查方法。
 */
public interface StudentMapper extends BaseMapper<Student> {
}
