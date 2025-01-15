package com.rosy.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class QueryWrapperUtil {

    /**
     * 添加等值条件
     *
     * @param queryWrapper 查询构造器
     * @param value        条件值
     * @param column       数据库字段映射
     * @param <T>          实体类型
     */
    public static <T> void addCondition(LambdaQueryWrapper<T> queryWrapper, Object value, SFunction<T, ?> column) {
        if (value != null) {
            queryWrapper.eq(column, value);
        }
    }

    /**
     * 添加模糊查询条件
     *
     * @param queryWrapper 查询构造器
     * @param value        条件值
     * @param column       数据库字段映射
     * @param <T>          实体类型
     */
    public static <T> void addLikeCondition(LambdaQueryWrapper<T> queryWrapper, String value, SFunction<T, ?> column) {
        if (StringUtils.isNotBlank(value)) {
            queryWrapper.like(column, value);
        }
    }

    /**
     * 添加排序条件
     *
     * @param queryWrapper 查询构造器
     * @param sortField    排序字段
     * @param sortOrder    排序顺序，"asc" 或 "desc"
     * @param defaultField 默认排序字段
     * @param <T>          实体类型
     */
    public static <T> void addSortCondition(LambdaQueryWrapper<T> queryWrapper, String sortField, String sortOrder, SFunction<T, ?> defaultField) {
        boolean isAsc = "asc".equalsIgnoreCase(Optional.ofNullable(sortOrder).orElse("asc"));
        if (StringUtils.isNotBlank(sortField)) {
            queryWrapper.orderBy(true, isAsc, defaultField);
        } else {
            queryWrapper.orderBy(true, isAsc, defaultField); // 默认排序字段
        }
    }
}
