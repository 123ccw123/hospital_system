package com.ccw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccw.commons.POIUtils;
import com.ccw.entity.Ordersetting;
import com.ccw.mapper.OrdersettingMapper;
import com.ccw.service.OrdersettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class OrdersettingServiceImpl extends ServiceImpl<OrdersettingMapper, Ordersetting> implements OrdersettingService {

    @Autowired
    private OrdersettingMapper ordersettingMapper;
    @Override
    public boolean importDtat(List<String[]> list) {
        if(!CollectionUtils.isEmpty(list)){
            for (String[] strings : list){
                String d = strings[0];
                //将日期由字符串类型转化为date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate date = LocalDate.parse(d,formatter);
                //从excle中获取日期
                Ordersetting ordersetting = new Ordersetting();
                ordersetting.setOrderdate(date);
                //从excle中获取预约人数
                String num = strings[1];
                ordersetting.setNumber(Integer.valueOf(num));

                //根据日期判断是否当天数据已经存在
                QueryWrapper<Ordersetting> wrapper = new QueryWrapper<>();
                wrapper.eq("orderDate",date);
                List<Ordersetting> selectList = ordersettingMapper.selectList(wrapper);
                if (CollectionUtils.isEmpty(selectList)){
                    ordersettingMapper.insert(ordersetting);
                }else {
                    ordersettingMapper.update(ordersetting,wrapper);
                }
            }
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> findDate(String date) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        //设置开始与结束的时间
        String beginDate=date+"-01";
        String overDate=date+"-31";
        QueryWrapper<Ordersetting> wrapper = new QueryWrapper<>();
        wrapper.between("orderDate",beginDate,overDate);
        List<Ordersetting> selectList = ordersettingMapper.selectList(wrapper);
        for (Ordersetting ordersetting : selectList) {
            HashMap<String, Object> map = new HashMap<>();
            LocalDate orderdate = ordersetting.getOrderdate();
            map.put("date",ordersetting.getOrderdate().getDayOfMonth());
            map.put("number",ordersetting.getNumber());
            map.put("reservations",ordersetting.getReservations());
            list.add(map);
        }
        return list;
    }

    @Override
    public void settingDate(Ordersetting ordersetting) {
        /*String date = (String) map.get("orderDate");
        Integer number = (Integer) map.get("number");*/
        QueryWrapper<Ordersetting> wrapper = new QueryWrapper<>();
        wrapper.eq("orderDate", ordersetting.getOrderdate());
        Ordersetting selectOne = ordersettingMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(selectOne)) {
            /*Ordersetting os = new Ordersetting();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parse = LocalDate.parse(date, formatter);
            os.setOrderdate(parse);
            os.setNumber(number);*/
            ordersettingMapper.insert(ordersetting);
        } else {
            /*Ordersetting os = new Ordersetting();
            os.setNumber(number);*/
            ordersettingMapper.update(ordersetting, wrapper);
        }
    }
}
