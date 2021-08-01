package com.ccw.service;

import com.ccw.commons.PageResult;
import com.ccw.commons.QueryPageBean;
import com.ccw.entity.Checkgroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
public interface CheckgroupService extends IService<Checkgroup> {
    boolean add(Integer[] checkitemIds, Checkgroup checkgroup);
    PageResult findByPage(QueryPageBean queryPageBean);
    boolean update(Integer[] checkitemIds, Checkgroup checkgroup);
    boolean deleteByGroupId(int id);

}
