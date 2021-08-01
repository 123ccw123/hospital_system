package com.ccw.mapper;

import com.ccw.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
public interface OrderMapper extends BaseMapper<Order> {
    List<Map<String,Object>> findHostSetmeal();
}
