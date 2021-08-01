package com.ccw.mapper;

import com.ccw.pojo.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-07-06
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    @Select("select role_code from sys_role r,sys_user_role ur where r.id=ur.role_id and ur.user_id=#{userId}")
    List<String> selectRoleCodesByUserId(Integer userId);
}
