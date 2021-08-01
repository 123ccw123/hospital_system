package com.ccw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccw.commons.PageResult;
import com.ccw.commons.QueryPageBean;
import com.ccw.entity.Checkgroup;
import com.ccw.entity.CheckgroupCheckitem;
import com.ccw.mapper.CheckgroupCheckitemMapper;
import com.ccw.mapper.CheckgroupMapper;
import com.ccw.service.CheckgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
@Transactional
public class CheckgroupServiceImpl extends ServiceImpl<CheckgroupMapper, Checkgroup> implements CheckgroupService {

    @Autowired
    private CheckgroupMapper checkgroupMapper;
    @Autowired
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;
    @Override
    public boolean add(Integer[] checkitemIds, Checkgroup checkgroup) {
        //添加检查组信息
        checkgroupMapper.insert(checkgroup);
        //获取到检查组的id
        Integer id = checkgroup.getId();
        //向检查组与检查项的中间表添加数据
        for (Integer checkitemId : checkitemIds) {
            //创建检查组与检查项的中间表
            CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
            checkgroupCheckitem.setCheckgroupId(id);
            checkgroupCheckitem.setCheckitemId(checkitemId);
            checkgroupCheckitemMapper.insert(checkgroupCheckitem);
        }return false;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        Page page =new Page(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        QueryWrapper<Checkgroup> wapper = new QueryWrapper<>();
        String string = queryPageBean.getQueryString();
        if(StringUtils.hasText(string)){
            wapper.eq("code",string).or()
                    .eq("name",string).or()
                    .eq("helpCode",string);
        }
        Page p = checkgroupMapper.selectPage(page, wapper);
        PageResult result = new PageResult(p.getTotal(),p.getRecords());
        return result;
    }

    @Override
    public boolean update(Integer[] checkitemIds, Checkgroup checkgroup) {
        //更新检查组数据
        checkgroupMapper.updateById(checkgroup);
        //根据检查组的id查询检查组与中间表的对应数据如果存在将其删除
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("checkgroup_id",checkgroup.getId());
        List<CheckgroupCheckitem> list = checkgroupCheckitemMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(list)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("checkgroup_id",checkgroup.getId());
            checkgroupCheckitemMapper.deleteByMap(map);
        }
        //添加新的与检查组匹配的检查项id到中间表中
        if (checkitemIds !=null && checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
                checkgroupCheckitem.setCheckitemId(checkitemId);
                checkgroupCheckitem.setCheckgroupId(checkgroup.getId());
                checkgroupCheckitemMapper.insert(checkgroupCheckitem);
            }
        }
        return false;
    }

    @Override
    public boolean deleteByGroupId(int id) {
        //根据检查表的id与检查项到他们的中间表进行删除
        HashMap<String, Object> map = new HashMap<>();
        map.put("checkgroup_id",id);
        checkgroupCheckitemMapper.deleteByMap(map);
        //删除检查组对应的信息
        checkgroupMapper.deleteById(id);
        return false;
    }
}
