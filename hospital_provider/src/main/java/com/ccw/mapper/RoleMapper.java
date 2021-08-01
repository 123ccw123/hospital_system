package com.ccw.mapper;

import com.ccw.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select keyword from t_user_role ur,t_role r where ur.role_id=r.id and ur.user_id=#{id}")
    List<String> selectRoleCodesByUserId(Integer id);
}
