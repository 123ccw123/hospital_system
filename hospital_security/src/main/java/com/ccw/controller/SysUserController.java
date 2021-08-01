package com.ccw.controller;


import com.ccw.common.ResultCode;
import com.ccw.common.ResultData;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-07-06
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @RequestMapping("/list")
    @PreAuthorize("hasAuthority('user:list')")//配置权限
    public ResultData list(){
        return ResultData.ok(ResultCode.SUCCESS,"访问用户查询界面成功!");
    }

    @GetMapping("/delete")
    @Secured("ROLE_admin")//配置角色
    public ResultData userDelete() {
        return ResultData.ok(ResultCode.SUCCESS, "访问用户删除界面成功!");
    }
}

