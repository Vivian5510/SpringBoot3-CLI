package com.rosy.main.domain.dto.item;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ItemUpdateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     * 必须为正整数，不能为空
     */
    @NotNull(message = "ID 不能为空")
    @Positive(message = "ID 必须为正整数")
    private Long id;

    /**
     * 名称
     * 最大长度 100，可选
     */
    @Size(max = 100, message = "名称长度不能超过 100 个字符")
    private String name;

    /**
     * 简介/内容
     * 最大长度 500，可选
     */
    @Size(max = 500, message = "简介长度不能超过 500 个字符")
    private String description;

    /**
     * 类型
     * 值范围为 1-127，可选
     */
    @Min(value = 1, message = "类型值不能小于 1")
    @Max(value = 127, message = "类型值不能大于 127")
    private Byte type;

    /**
     * 状态
     * 只能为 0 或 1，可选
     */
    @Min(value = 0, message = "状态值只能为 0 或 1")
    @Max(value = 1, message = "状态值只能为 0 或 1")
    private Byte status;

    /**
     * 排序字段，用于控制物品显示顺序
     * 必须为非负整数，可选
     */
    @PositiveOrZero(message = "排序字段必须为非负整数")
    private Integer sortOrder;
}
