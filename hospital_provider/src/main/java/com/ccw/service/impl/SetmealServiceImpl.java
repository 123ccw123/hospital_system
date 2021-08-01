package com.ccw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccw.commons.PageResult;
import com.ccw.commons.QueryPageBean;
import com.ccw.entity.*;
import com.ccw.mapper.*;
import com.ccw.service.SetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealCheckgroupMapper setmealCheckgroupMapper;
    @Autowired
    private CheckgroupMapper checkgroupMapper;
    @Autowired
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;
    @Autowired
    private CheckitemMapper checkitemMapper;
    @Override
    public boolean save(Integer[] checkgroupIds, Setmeal setmeal) {
        //保存套餐信息
        setmealMapper.insert(setmeal);
        //获取检查组的id
        Integer id = setmeal.getId();
        if (checkgroupIds !=null && checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
                setmealCheckgroup.setCheckgroupId(checkgroupId);
                setmealCheckgroup.setSetmealId(id);
                setmealCheckgroupMapper.insert(setmealCheckgroup);
            }
        }
        return false;
    }

    @Override
    public PageResult findPageById(QueryPageBean q) {
        //封装分页信息
        Page page = new Page(q.getCurrentPage(),q.getPageSize());
        //创建条件对象
        QueryWrapper<Setmeal> wrapper = new QueryWrapper<>();
        //获取查询条件
        String string = q.getQueryString();
        //判断查询条件
        if(StringUtils.hasText(string)){
            wrapper.eq("code",string).or()
                    .eq("name",string).or()
                    .eq("helpCode",string);
        }
        //进行分页
        Page page1 = setmealMapper.selectPage(page, wrapper);
        //将结果封装到自定义的结果中
        PageResult result = new PageResult(page1.getTotal(), page1.getRecords());
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        //根据检查表的id与检查套餐的id到他中间表中进行删除
        HashMap<String, Object> map = new HashMap<>();
        map.put("setmeal_id",id);
        System.out.println(map);
        setmealCheckgroupMapper.deleteByMap(map);
        //删除对应检查表的信息
        setmealMapper.deleteById(id);
        return false;
    }

    @Override
    public List<Integer> findByCheckGroupId(int sid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("setmeal_id",sid);
        wrapper.select("checkgroup_id");
        List<Integer> ids = new ArrayList<>();
        List<Object> list = setmealCheckgroupMapper.selectObjs(wrapper);
        for (Object o : list) {
            Integer i = (Integer)o;
            ids.add(i);
        }
        //List<Integer> collect = list.stream().map(o -> (Integer) o).collect(Collectors.toList());
        return ids;
    }

    @Override
    public boolean editUpdate(Integer[] checkgroupIds, Setmeal setmeal) {
        //更新检查组数据
        setmealMapper.updateById(setmeal);
        //根据套餐组的id查询套餐组与中间表是否存在关联数据，如果有将其删除
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("setmeal_id",setmeal.getId());
        List list = setmealCheckgroupMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(list)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("setmeal_id",setmeal.getId());
            setmealCheckgroupMapper.deleteByMap(map);
        }
        //添加新的检查项
        if(checkgroupIds !=null && checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
                setmealCheckgroup.setSetmealId(setmeal.getId());
                setmealCheckgroup.setCheckgroupId(checkgroupId);
                setmealCheckgroupMapper.insert(setmealCheckgroup);
            }
        }
        return false;
    }

    @Override
    public Setmeal findById(int id) {
        //根据id获取到套餐的所有信息
        Setmeal setmeal = setmealMapper.selectById(id);
        //根据套餐的id到套餐与检查组的中间表获取到检查组的id
        QueryWrapper<SetmealCheckgroup> wrapper = new QueryWrapper<>();
        wrapper.eq("checkgroup_id",id);
        List<SetmealCheckgroup> setmealCheckgroups = setmealCheckgroupMapper.selectList(wrapper);
        //从套餐与检查组的中间表中获取到检查组的id
        ArrayList<Integer> checkGroupIds = new ArrayList<>();
        for (SetmealCheckgroup setmealCheckgroup : setmealCheckgroups) {
            Integer checkgroupId = setmealCheckgroup.getCheckgroupId();
            checkGroupIds.add(checkgroupId);
            }
        //根据检查组id获取到检查组中的信息
        List<Checkgroup> checkgroups = checkgroupMapper.selectBatchIds(checkGroupIds);

        for (Checkgroup checkgroup : checkgroups) {
            //根据id到检查组与检查项的中间表查询到检查组的信息
            QueryWrapper<CheckgroupCheckitem> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("checkgroup_id",checkgroup.getId());
            List<CheckgroupCheckitem> checkgroupCheckitems = checkgroupCheckitemMapper.selectList(wrapper1);
            ArrayList<Integer> checkgroupcheckitems = new ArrayList<>();
            for (CheckgroupCheckitem checkgroupCheckitem : checkgroupCheckitems) {
                Integer checkitemId = checkgroupCheckitem.getCheckitemId();
                checkgroupcheckitems.add(checkitemId);
            }
            //根据检查项id获取检查项信息
            List<Checkitem> checkitems = checkitemMapper.selectBatchIds(checkgroupcheckitems);
            //将检查项信息添加到检查组中
            checkgroup.setCheckItems(checkitems);
        }
        //将检查组的信息与套餐建立联系
        setmeal.setCheckGroups(checkgroups);
        return setmeal;
    }
}
