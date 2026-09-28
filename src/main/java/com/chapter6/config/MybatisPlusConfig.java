package com.chapter6.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * 第六章第二节课新增效果：MyBatis-Plus 插件配置。
 *
 * 分页插件负责生成 MySQL 分页 SQL；防全表插件阻止没有条件的更新和删除；
 * 乐观锁插件配合 Student.version 上的 @Version 防止并发更新互相覆盖。
 */
public final class MybatisPlusConfig {

    private MybatisPlusConfig() {
    }

    public static MybatisPlusInterceptor buildInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        PaginationInnerInterceptor pagination = new PaginationInnerInterceptor(DbType.MYSQL);
        pagination.setMaxLimit(500L);
        interceptor.addInnerInterceptor(pagination);

        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
