package com.ccw.controller;


import com.ccw.commons.MessageConstant;
import com.ccw.commons.Result;
import com.ccw.entity.Setmeal;
import com.ccw.service.SetmealService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/setmeal")
public class HospitalMobileController {
    @Reference
    private SetmealService setmealService;
    /*移动端获取数据列表*/
    @RequestMapping("/list")
    public Result list(){
        try {
            List<Setmeal> list = setmealService.list();
            return new Result(true,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }
    /*根据套餐组id回显所有套餐的详细信息*/
    @RequestMapping("/detail")
    public Result detail(int id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    //根据id查询套餐
    @RequestMapping("/findInfoById")
    public Result findInfoById(int id){
        try {
            Setmeal result = setmealService.getById(id);
            return new Result(true,result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}

