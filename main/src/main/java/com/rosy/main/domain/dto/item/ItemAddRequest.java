package com.rosy.main.domain.dto.item;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ItemAddRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     * 必填，长度限制为 1 到 100 个字符
     */
    @NotBlank(message = "名称不能为空")
    @Size(max = 100, message = "名称长度不能超过 100 个字符")
    private String name;

    /**
     * 简介/内容
     * 非必填，最大长度 500
     */
    @Size(max = 500, message = "简介长度不能超过 500 个字符")
    private String description;

    /**
     * 类型
     * 必填，值范围为 1 到 127
     */
    @NotNull(message = "类型不能为空")
    @Min(value = 1, message = "类型值不能小于 1")
    @Max(value = 127, message = "类型值不能大于 127")
    private Byte type;

    /**
     * 状态
     * 必填，值范围为 0 或 1
     */
    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态值只能为 0 或 1")
    @Max(value = 1, message = "状态值只能为 0 或 1")
    private Byte status;

    /**
     * 排序字段，用于控制物品显示顺序
     * 非必填，值必须为正整数
     */
    @PositiveOrZero(message = "排序字段必须为非负整数")
    private Integer sortOrder;
}
