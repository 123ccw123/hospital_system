package com.ccw.controller;

import com.ccw.commons.MessageConstant;
import com.ccw.commons.Result;
import com.ccw.entity.OrderInfo;
import com.ccw.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody OrderInfo orderInfo){
        try {
            return orderService.saveOrder(orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FULLd);
        }
    }
    /*将会员信息返回显示到页面上*/
    @RequestMapping("/findOrderById")
    public Result findOrderById(int id){
        try {
            Map<String, Object> orderSetmealById = orderService.findOrderSetmealById(id);
            return new Result(true,orderSetmealById);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
