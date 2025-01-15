package com.rosy.web.controller.main;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rosy.common.annotation.AuthCheck;
import com.rosy.common.annotation.ValidateRequest;
import com.rosy.common.domain.entity.AjaxResult;
import com.rosy.common.domain.entity.IdRequest;
import com.rosy.common.enums.ErrorCode;
import com.rosy.common.exception.BusinessException;
import com.rosy.common.utils.PageUtils;
import com.rosy.common.utils.ThrowUtils;
import com.rosy.main.domain.dto.user.*;
import com.rosy.main.domain.entity.User;
import com.rosy.main.domain.vo.LoginUserVO;
import com.rosy.main.domain.vo.UserVO;
import com.rosy.main.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Rosy
 * @since 2025-01-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    // region 登录相关

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ValidateRequest
    public AjaxResult userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return AjaxResult.success(result);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ValidateRequest
    public AjaxResult userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return AjaxResult.success(loginUserVO);
    }

    /**
     * 用户注销
     */
    @PostMapping("/logout")
    @ValidateRequest
    public AjaxResult userLogout(HttpServletRequest request) {
        boolean result = userService.userLogout(request);
        return AjaxResult.success(result);
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/get/login")
    public AjaxResult getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return AjaxResult.success(userService.getLoginUserVO(user));
    }

    // endregion

    // region 增删改查

    /**
     * 创建用户
     */
    @PostMapping("/add")
    @ValidateRequest
    public AjaxResult addUser(@RequestBody UserAddRequest userAddRequest) {
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return AjaxResult.success(user.getId());
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @ValidateRequest
    public AjaxResult deleteUser(@RequestBody IdRequest idRequest) {
        boolean result = userService.removeById(idRequest.getId());
        return AjaxResult.success(result);
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    @ValidateRequest
    public AjaxResult updateUser(@RequestBody UserUpdateRequest userUpdateRequest,
                                 HttpServletRequest request) {
        if (userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = BeanUtil.copyProperties(userUpdateRequest, User.class);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return AjaxResult.success(true);
    }

    /**
     * 根据 id 获取用户（仅管理员）
     */
    @GetMapping("/get")
    public AjaxResult getUserById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return AjaxResult.success(user);
    }

    /**
     * 根据 id 获取包装类
     */
    @GetMapping("/get/vo")
    public AjaxResult getUserVOById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        AjaxResult response = getUserById(id);
        User user = (User) response.get(AjaxResult.DATA_TAG);
        return AjaxResult.success(userService.getUserVO(user));
    }

    /**
     * 分页获取用户列表（仅管理员）
     */
    @PostMapping("/list/page")
    @ValidateRequest
    @AuthCheck(mustRole = "admin")
    public AjaxResult listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, size), userService.getQueryWrapper(userQueryRequest));
        return AjaxResult.success(userPage);
    }

    /**
     * 分页获取用户封装列表
     */
    @PostMapping("/list/page/vo")
    @ValidateRequest
    public AjaxResult listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<User> userPage = userService.page(new Page<>(current, size), userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = PageUtils.convert(userPage, userService::getUserVO);
        return AjaxResult.success(userVOPage);
    }

    // endregion

    /**
     * 更新个人信息
     */

    @PostMapping("/update/my")
    @ValidateRequest
    public AjaxResult updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        User user = BeanUtil.copyProperties(userUpdateMyRequest, User.class);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return AjaxResult.success(true);
    }
}
