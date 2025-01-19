package com.rosy.main.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rosy.common.enums.ErrorCode;
import com.rosy.common.exception.BusinessException;
import com.rosy.common.utils.QueryWrapperUtil;
import com.rosy.main.domain.dto.item.ItemQueryRequest;
import com.rosy.main.domain.entity.Item;
import com.rosy.main.domain.vo.ItemVO;
import com.rosy.main.mapper.ItemMapper;
import com.rosy.main.service.IItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 物品表 服务实现类
 * </p>
 *
 * @author Rosy
 * @since 2025-01-19
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {
    @Override
    public ItemVO getItemVO(Item item) {
        return Optional.ofNullable(item)
                .map(i -> BeanUtil.copyProperties(i, ItemVO.class))
                .orElse(null);
    }


    @Override
    public LambdaQueryWrapper<Item> getQueryWrapper(ItemQueryRequest itemQueryRequest) {
        if (itemQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper<>();

        // 动态添加条件
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getId(), Item::getId);
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getName(), Item::getName);
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getDescription(), Item::getDescription);
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getType(), Item::getType);
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getStatus(), Item::getStatus);
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getCreatorId(), Item::getCreatorId);
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getCreateTime(), Item::getCreateTime);
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getUpdaterId(), Item::getUpdaterId);
        QueryWrapperUtil.addCondition(queryWrapper, itemQueryRequest.getUpdateTime(), Item::getUpdateTime);

        // 添加排序条件
        QueryWrapperUtil.addSortCondition(queryWrapper,
                itemQueryRequest.getSortField(),
                itemQueryRequest.getSortOrder(),
                Item::getId);

        return queryWrapper;
    }

}
