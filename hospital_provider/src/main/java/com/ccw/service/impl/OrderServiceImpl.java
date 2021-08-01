package com.ccw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccw.commons.MessageConstant;
import com.ccw.commons.Result;
import com.ccw.entity.*;
import com.ccw.mapper.MemberMapper;
import com.ccw.mapper.OrderMapper;
import com.ccw.mapper.OrdersettingMapper;
import com.ccw.mapper.SetmealMapper;
import com.ccw.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrdersettingMapper ordersettingMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public Result saveOrder(OrderInfo orderInfo) {
        //根据用户提供的日期判断当天是否可以预约
        QueryWrapper<Ordersetting> wrapper = new QueryWrapper<>();
        wrapper.eq("orderDate",orderInfo.getOrderDate());
        Ordersetting ordersetting = ordersettingMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(ordersetting)){
            System.out.println("当天不可逾越");
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

      //预约的日期当天是否预约满
        Integer reservations = ordersetting.getReservations();
        Integer number = ordersetting.getNumber();
        if(reservations.equals(number)){
            System.out.println("预约满");
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //该用户是否为会员
        QueryWrapper<Member> wrappers = new QueryWrapper<>();
        wrappers.eq("idCard",orderInfo.getIdCard());
        Member member = memberMapper.selectOne(wrappers);
        Member member1 = null;
        //是会员的话同一个用户在一天只能预约一个项目一次
        if(!ObjectUtils.isEmpty(member)){
            System.out.println("该用户是会员");
            Integer id = member.getId();
            QueryWrapper<Order> wrapperss = new QueryWrapper<>();
            wrapperss.eq("member_id",id)
                     .eq("orderDate",orderInfo.getOrderDate())
                     .eq("setmeal_id",orderInfo.getSetmealId());
            Order order = orderMapper.selectOne(wrapperss);
            if (!ObjectUtils.isEmpty(order)){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {

            //不是会员的话该用户需要添加变成会员
            member1 = new Member();
            member1.setName(orderInfo.getName());
            member1.setIdcard(orderInfo.getIdCard());
            member1.setSex(orderInfo.getSex());
            member1.setRegtime(LocalDate.now());
            member1.setPhonenumber(orderInfo.getTelephone());
            memberMapper.insert(member1);
        }
            //添加数据到订单中
            Order order = new Order();

        if (!ObjectUtils.isEmpty(member)){
            order.setMemberId(member.getId());
        }else {
            order.setMemberId(member1.getId());
        }
        order.setOrderdate(orderInfo.getOrderDate());
        order.setOrderstatus("未进行检查！");
        order.setOrdertype("微信预约");
        order.setSetmealId(orderInfo.getSetmealId());
        orderMapper.insert(order);
        //更新预约人数到预约表中
        int i =ordersetting.getReservations()+1;
        ordersetting.setReservations(i);
        ordersettingMapper.updateById(ordersetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    @Override
    public Map<String, Object> findOrderSetmealById(int id) {

        HashMap<String, Object> map = new HashMap<>();
        //根据传入id查询预约族的信息
        Order order = orderMapper.selectById(id);
        map.put("orderDate",order.getOrderdate());
        map.put("orderType",order.getOrdertype());

        //从order中获取会员id
        Integer memberId = order.getMemberId();
        Member member = memberMapper.selectById(memberId);
        map.put("member",member.getName());

        //从会员中获取套餐id
        Integer setmealId = order.getSetmealId();
        Setmeal setmeal = setmealMapper.selectById(setmealId);
        map.put("setmeal",setmeal.getName());
        return map;
    }
}
