package com.rosy.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
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
        // 判断排序顺序，默认升序
        boolean isAsc = "asc".equalsIgnoreCase(Optional.ofNullable(sortOrder).orElse("asc"));

        // 如果指定了排序字段且有效
        if (StringUtils.isNotBlank(sortField)) {
            try {
                // 尝试将排序字段转换为对应的 Lambda 表达式
                SFunction<T, ?> field = getFieldByName(sortField, defaultField);
                queryWrapper.orderBy(true, isAsc, field);
            } catch (IllegalArgumentException e) {
                // 如果字段无效，回退到默认排序字段
                queryWrapper.orderBy(true, isAsc, defaultField);
            }
        } else {
            // 使用默认排序字段
            queryWrapper.orderBy(true, isAsc, defaultField);
        }
    }

    /**
     * 根据字段名获取对应的 SFunction
     *
     * @param fieldName    字段名
     * @param defaultField 默认字段（如果找不到时返回）
     * @param <T>          实体类型
     * @return 对应的 SFunction
     */
    private static <T> SFunction<T, ?> getFieldByName(String fieldName, SFunction<T, ?> defaultField) {
        // 示例逻辑：可以根据实际业务实现字段映射
        // 假设字段映射是一个 Map<String, SFunction<T, ?>>
        Map<String, SFunction<T, ?>> fieldMapping = getFieldMapping();

        // 返回字段映射或默认字段
        return Optional.ofNullable(fieldMapping.get(fieldName))
                .orElseThrow(() -> new IllegalArgumentException("Invalid sort field: " + fieldName));
    }

    /**
     * 获取字段映射（示例方法）
     *
     * @param <T> 实体类型
     * @return 字段映射
     */
    private static <T> Map<String, SFunction<T, ?>> getFieldMapping() {
        // TODO: 实现字段映射逻辑
        return new HashMap<>();
    }

}
