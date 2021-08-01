package com.ccw.mapper;

import com.ccw.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
public interface MemberMapper extends BaseMapper<Member> {
    List<Map<String,Object>> selectCountBySetmealName();
}
