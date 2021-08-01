package com.ccw.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccw.mapper.SysMenuMapper;
import com.ccw.mapper.SysRoleMapper;
import com.ccw.mapper.SysUserMapper;
import com.ccw.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    /**
     * 通过用户名加载用户
     * 并且封装了该用户对应的角色与权限
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<SysUser>wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",s);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        System.out.println("打印的值" + sysUser);
        if(ObjectUtils.isEmpty(sysUser)){
            throw new RuntimeException("用户名或密码不正确");
        }
        List<GrantedAuthority>list=new ArrayList<>();
        //根据用户id查询对应角色
        List<String> roleCodes = sysRoleMapper.selectRoleCodesByUserId(sysUser.getId());
        for (String roleCode : roleCodes) {
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority("ROLE_"+roleCode);
            list.add(simpleGrantedAuthority);
        }
        //根据用户id查询对应的权限菜单
        List<String> perms = sysMenuMapper.selectMenuPermsByUserId(sysUser.getId());
        for (String perm : perms) {
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(perm);
            list.add(simpleGrantedAuthority);
        }
        return new User(s,sysUser.getPassword(),list);
    }
}
