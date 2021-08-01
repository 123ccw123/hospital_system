package com.ccw.mapper;

import com.ccw.entity.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("select keyword from t_user_role ur," +
            "t_role_permission rp,t_permission p " +
            "where rp.permission_id=p.id and ur.role_id=rp.role_id and ur.user_id=#{id}")
    List<String> selectMenuPermsByUserId(Integer id);
}
