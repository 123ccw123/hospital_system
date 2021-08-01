package com.ccw.mapper;

import com.ccw.pojo.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-07-06
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    @Select("select perms from sys_user_role ur,sys_role_menu rm,sys_menu m " +
            "where ur.role_id=rm.role_id and rm.menu_id=m.id and ur.user_id=#{userId}")
    List<String> selectMenuPermsByUserId(Integer userId);
}
