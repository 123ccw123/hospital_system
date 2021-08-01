package com.ccw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccw.entity.User;
import com.ccw.mapper.PermissionMapper;
import com.ccw.mapper.RoleMapper;
import com.ccw.mapper.UserMapper;
import com.ccw.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public User findUserByUserName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<String> findRolesByUid(int uid) {
        return roleMapper.selectRoleCodesByUserId(uid);
    }

    @Override
    public List<String> findPermissionByUid(int uid) {
        return permissionMapper.selectMenuPermsByUserId(uid);
    }
}
