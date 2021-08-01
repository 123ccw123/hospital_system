package com.ccw.service;

import com.ccw.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
public interface UserService extends IService<User> {
    User findUserByUserName(String username);

    List<String>findRolesByUid(int uid);

    List<String>findPermissionByUid(int uid);
}
