package com.rosy.framework.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

//TODO: 1.这里的createTime和updateTime的类型要根据数据库字段的类型来修改
//TODO: 2.这里的createBy和updateBy的类型要根据数据库字段的类型来修改, 生成的逻辑也要根据实际情况来修改，这里0表示系统超级管理员或者默认值
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "createBy", Long.class, 0L);
        this.strictInsertFill(metaObject, "updateBy", Long.class, 0L);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateBy", Long.class, 0L);
    }
}