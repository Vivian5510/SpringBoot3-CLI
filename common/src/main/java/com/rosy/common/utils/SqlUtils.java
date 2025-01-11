package com.rosy.common.utils;

/**
 * SQL 工具
 * <p>
 * 校验排序字段是否合法（防止 SQL 注入）
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     */
    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}