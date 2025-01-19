package com.rosy.web.controller.main;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosy.common.annotation.ValidateRequest;
import com.rosy.common.domain.entity.ApiResponse;
import com.rosy.common.domain.entity.IdRequest;
import com.rosy.common.enums.ErrorCode;
import com.rosy.common.exception.BusinessException;
import com.rosy.common.utils.PageUtils;
import com.rosy.common.utils.ThrowUtils;
import com.rosy.main.domain.dto.item.ItemAddRequest;
import com.rosy.main.domain.dto.item.ItemQueryRequest;
import com.rosy.main.domain.dto.item.ItemUpdateRequest;
import com.rosy.main.domain.entity.Item;
import com.rosy.main.domain.vo.ItemVO;
import com.rosy.main.service.IItemService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 物品表 前端控制器
 * </p>
 *
 * @author Rosy
 * @since 2025-01-19
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    @Resource
    IItemService itemService;

    // region 增删改查

    /**
     * 创建
     */
    @PostMapping("/add")
    @ValidateRequest
    public ApiResponse addItem(@RequestBody ItemAddRequest itemAddRequest) {
        Item item = new Item();
        BeanUtils.copyProperties(itemAddRequest, item);
        boolean result = itemService.save(item);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ApiResponse.success(item.getId());
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ValidateRequest
    public ApiResponse deleteItem(@RequestBody IdRequest idRequest) {
        boolean result = itemService.removeById(idRequest.getId());
        return ApiResponse.success(result);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @ValidateRequest
    public ApiResponse updateItem(@RequestBody ItemUpdateRequest itemUpdateRequest,
                                  HttpServletRequest request) {
        if (itemUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Item item = BeanUtil.copyProperties(itemUpdateRequest, Item.class);
        boolean result = itemService.updateById(item);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ApiResponse.success(true);
    }

    /**
     * 根据 id 获取
     */
    @GetMapping("/get")
    public ApiResponse getItemById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Item item = itemService.getById(id);
        ThrowUtils.throwIf(item == null, ErrorCode.NOT_FOUND_ERROR);
        return ApiResponse.success(item);
    }

    /**
     * 根据 id 获取包装类
     */
    @GetMapping("/get/vo")
    public ApiResponse getItemVOById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiResponse response = getItemById(id);
        Item item = (Item) response.get(ApiResponse.DATA_TAG);
        return ApiResponse.success(itemService.getItemVO(item));
    }

    /**
     * 分页获取列表
     */
    @PostMapping("/list/page")
    @ValidateRequest
    public ApiResponse listItemByPage(@RequestBody ItemQueryRequest itemQueryRequest) {
        long current = itemQueryRequest.getCurrent();
        long size = itemQueryRequest.getPageSize();
        Page<Item> itemPage = itemService.page(new Page<>(current, size), itemService.getQueryWrapper(itemQueryRequest));
        return ApiResponse.success(itemPage);
    }

    /**
     * 分页获取封装列表
     */
    @PostMapping("/list/page/vo")
    @ValidateRequest
    public ApiResponse listItemVOByPage(@RequestBody ItemQueryRequest itemQueryRequest) {
        long current = itemQueryRequest.getCurrent();
        long size = itemQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Item> itemPage = itemService.page(new Page<>(current, size), itemService.getQueryWrapper(itemQueryRequest));
        Page<ItemVO> itemVOPage = PageUtils.convert(itemPage, itemService::getItemVO);
        return ApiResponse.success(itemVOPage);
    }

    // endregion
}
