package com.ccw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ccw.commons.PageResult;
import com.ccw.commons.QueryPageBean;
import com.ccw.entity.Checkgroup;
import com.ccw.entity.Checkitem;
import com.ccw.mapper.CheckgroupCheckitemMapper;
import com.ccw.mapper.CheckitemMapper;
import com.ccw.service.CheckitemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class CheckitemServiceImpl extends ServiceImpl<CheckitemMapper, Checkitem> implements CheckitemService {

    @Autowired
    private CheckitemMapper checkitemMapper;
    @Autowired
    private CheckgroupCheckitemMapper checkgroupCheckitemMapper;
    @Override
    public PageResult findByPage(QueryPageBean q) {
        //封装分页信息
        Page page = new Page(q.getCurrentPage(),q.getPageSize());
        //创建条件对象
        QueryWrapper<Checkitem> wapper = new QueryWrapper<>();
        //获取查询条件
        String queryString = q.getQueryString();
        //判断查询条件
        if(StringUtils.hasText(queryString)){
            wapper.eq("code",queryString).or().eq("name",queryString);
        }
        //进行分页
        Page p = checkitemMapper.selectPage(page, wapper);
        //封装到自定义的分页结果中
        PageResult result = new PageResult(p.getTotal(), p.getRecords());
        return result;
    }


    @Override
    public List<Integer> findByCheckGroupId(int gid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("checkgroup_id",gid);
        wrapper.select("checkitem_id");
        List<Integer> ids = new ArrayList<>();
        List<Object> list = checkgroupCheckitemMapper.selectObjs(wrapper);
        for (Object o : list) {
            Integer i = (Integer)o;
            ids.add(i);
        }
        //List<Integer> collect = list.stream().map(o -> (Integer) o).collect(Collectors.toList());
        return ids;
    }
}
