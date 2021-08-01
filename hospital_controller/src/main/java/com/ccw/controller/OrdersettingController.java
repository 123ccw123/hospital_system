package com.ccw.controller;


import com.ccw.commons.MessageConstant;
import com.ccw.commons.POIUtils;
import com.ccw.commons.Result;
import com.ccw.entity.Ordersetting;
import com.ccw.service.OrdersettingService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Reference
    private OrdersettingService ordersettingService;

    @RequestMapping("/uploadTempleate")
    public Result upload(@RequestParam("excelFile")MultipartFile multipartFile){
        try {
            List<String[]> list = POIUtils.readExcel(multipartFile);
            ordersettingService.importDtat(list);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
    @RequestMapping("/showDate")
    public Result showDate(String date){
        try {
            List<Map<String, Object>> date1 = ordersettingService.findDate(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,date1);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
    @RequestMapping("/setting")
    public Result setting(@RequestBody Ordersetting ordersetting){
        try {
            ordersettingService.settingDate(ordersetting);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}

