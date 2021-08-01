package com.ccw.service;

import com.ccw.commons.Result;
import com.ccw.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ccw.entity.OrderInfo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
public interface OrderService extends IService<Order> {
    Result saveOrder(OrderInfo orderInfo);
    Map<String,Object> findOrderSetmealById(int id);
}
