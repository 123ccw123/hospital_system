package com.ccw.service;

import com.ccw.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
public interface MemberService extends IService<Member> {
    List<Integer> findMemberCount(List<String> months);
    List<Map<String,Object>> finCountBySetmeal();
    Map<String,Object> findBusinessReportData();
}
