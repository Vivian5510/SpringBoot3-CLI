package com.rosy.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtils {
    public static <T, R> Page<R> convert(Page<T> sourcePage, Function<T, R> converter) {
        Page<R> targetPage = new Page<>(sourcePage.getCurrent(), sourcePage.getSize(), sourcePage.getTotal());
        List<R> convertedRecords = sourcePage.getRecords().stream().map(converter).collect(Collectors.toList());
        targetPage.setRecords(convertedRecords);
        return targetPage;
    }
}
