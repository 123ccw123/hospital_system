package com.ccw.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role_menu")
public class RoleMenu extends Model {

    private static final long serialVersionUID = 1L;

      private Integer roleId;

    private Integer menuId;


}
