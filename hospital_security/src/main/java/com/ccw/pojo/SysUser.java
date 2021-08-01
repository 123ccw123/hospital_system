package com.ccw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zs
 * @since 2021-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实名称
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别10:男;11:女;12:其他
     */
    private Integer sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 状态10:正常;11:锁定;12:注销
     */
    private Integer status;

    /**
     * 删除标识0:未删除;1:已删除
     */
    private Integer delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    //@TableField(exist = false)
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    //@TableField(exist = false)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


}
