package com.rosy.main.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ItemVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 简介/内容
     */
    private String description;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 类型
     */
    private Byte status;

    /**
     * 创建者ID，关联用户表
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者ID，关联用户表
     */
    private Long updaterId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 排序字段，用于控制物品显示顺序
     */
    private Integer sortOrder;
}
