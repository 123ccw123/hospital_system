package com.ccw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccw.commons.DateUtils;
import com.ccw.entity.Member;
import com.ccw.entity.Order;
import com.ccw.mapper.MemberMapper;
import com.ccw.mapper.OrderMapper;
import com.ccw.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List<Integer> findMemberCount(List<String> months) {
        List<Integer> list = new ArrayList<>();
        for (String month : months) {
            //获取到每个月份并组合成日期
            String d = month+".31";
            try {
                //转化时间格式进行比较
                Date date = new SimpleDateFormat("yyyy.MM.dd").parse(d);
                //再次用当前时间与数据库中的时间进行比较
                QueryWrapper<Member> wrapper = new QueryWrapper<>();
                wrapper.lt("regTime",date);
                //查询每个月新增会员人数
                Integer count = memberMapper.selectCount(wrapper);
                //将每个月注册会员人数添加到集合中
                list.add(count);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /*获取预约的每一个套餐数量*/
    @Override
    public List<Map<String, Object>> finCountBySetmeal() {

        return memberMapper.selectCountBySetmealName();
    }

    @Override
    public Map<String, Object> findBusinessReportData() {
        HashMap<String, Object> map = new HashMap<>();

        try {
            /*获取今天新增加的会员*/
            //获取当前日期
            String date = DateUtils.parseDate2String(new Date());
            //根据会员表会员注册日期查询今天注册会员人数
            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.eq("regTime",date);
            //查询得到今天注册会员人数
            Integer count = memberMapper.selectCount(wrapper);
            map.put("todayNewMember",count);

            /*查询总会员数*/
            map.put("totalMember",memberMapper.selectCount(null));

            /*查询本周新增会员人数*/
            //查询本周周一时间
            Date monday = DateUtils.getThisWeekMonday();
            //清空wapper中的数据
            wrapper.clear();
            //比较得到在本周一以前的日期
            wrapper.ge("regTime",monday);
            //获取人数
            Integer count1 = memberMapper.selectCount(wrapper);
            map.put("thisWeekNewMember",count1);

            /*查询本月新增会员人数*/
            //获取当前月第一天
            Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();
            wrapper.clear();
            //比较得到在今天以前的日期
            wrapper.ge("regTime",firstDay4ThisMonth);
            //获取人数
            Integer count2 = memberMapper.selectCount(wrapper);
            map.put("thisMonthNewMember",count2);

            /*获取热门套餐人数*/
            List<Map<String, Object>> count3 = orderMapper.findHostSetmeal();
            map.put("hotSetmeal",count3);

            /*今天的预约人数*/
            QueryWrapper<Order> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("orderDate",date);
            Integer count4 = orderMapper.selectCount(wrapper1);
            map.put("todayOrderNumber",count4);

            /*今天到诊人数*/
            wrapper1.eq("orderStatus","已到诊");
            Integer count5 = orderMapper.selectCount(wrapper1);
            map.put("todayVisitsNumber",count5);

            /*本周预约人数*/
            wrapper1.clear();
            wrapper1.ge("orderDate",monday);
            Integer count6 = orderMapper.selectCount(wrapper1);
            map.put("thisWeekOrderNumber",count6);

            /*本周到诊人数*/
            wrapper1.eq("orderStatus","已到诊");
            Integer count9 = orderMapper.selectCount(wrapper1);
            map.put("thisWeekVisitsNumber",count9);

            /*本月已经预约的人数*/
            wrapper1.clear();
            wrapper1.ge("orderDate",firstDay4ThisMonth);
            Integer count7 = orderMapper.selectCount(wrapper1);
            map.put("thisMonthOrderNumber",count7);

            /*本月已到诊人数*/
            wrapper1.eq("orderStatus",wrapper1);
            Integer count8 = orderMapper.selectCount(wrapper1);
            map.put("thisMonthVisitsNumber",count8);
            map.put("reportDate",date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
