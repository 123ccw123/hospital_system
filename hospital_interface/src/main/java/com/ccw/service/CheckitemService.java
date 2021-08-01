package com.ccw.service;

import com.ccw.commons.PageResult;
import com.ccw.commons.QueryPageBean;
import com.ccw.entity.Checkitem;
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
public interface CheckitemService extends IService<Checkitem> {

    PageResult findByPage(QueryPageBean q);
    List<Integer>findByCheckGroupId(int gid);
}
