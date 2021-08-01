package com.ccw.service;

import com.ccw.commons.PageResult;
import com.ccw.commons.QueryPageBean;
import com.ccw.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
public interface SetmealService extends IService<Setmeal> {
    boolean save(Integer[] checkgroupIds, Setmeal setmeal);
    PageResult findPageById(QueryPageBean q );
    boolean deleteById(int id);
    List<Integer> findByCheckGroupId(int sid);
    boolean editUpdate(Integer[] checkgroupIds,Setmeal setmeal);
    Setmeal findById(int id);
}
