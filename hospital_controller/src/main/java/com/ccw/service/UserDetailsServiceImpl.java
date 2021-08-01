package com.ccw.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * name:offcnpe_parent
 * author: xiaoming
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 通过用户名加载用户
     * 并且封装了该用户对应的角色与权限
     * @params
     * @return
     * @throws UsernameNotFoundException
     */
    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<com.ccw.entity.User>wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",s);
        com.ccw.entity.User user = userService.findUserByUserName(s);
        System.out.println("打印的值" + user);
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("用户名或密码不正确");
        }
        List<GrantedAuthority>list=new ArrayList<>();
        //根据用户id查询对应角色
        //List<String> roleCodes = roleMapper.selectRoleCodesByUserId(User.getId());
        List<String> roleCodes = userService.findRolesByUid(user.getId());
        for (String roleCode : roleCodes) {
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(roleCode);
            list.add(simpleGrantedAuthority);
        }
        //根据用户id查询对应的权限菜单
        //List<String> perms = permissionMapper.selectMenuPermsByUserId(User.getId());
        List<String> permission = userService.findPermissionByUid(user.getId());
        for (String perm : permission) {
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(perm);
            list.add(simpleGrantedAuthority);
        }
        return new User(s,user.getPassword(),list);
    }
}
