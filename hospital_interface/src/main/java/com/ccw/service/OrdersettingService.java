package com.ccw.service;

import com.ccw.entity.Ordersetting;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
public interface OrdersettingService extends IService<Ordersetting> {

    boolean importDtat(List<String[]> list);
    List<Map<String,Object>> findDate(String date);
    void settingDate(Ordersetting ordersetting);
}
