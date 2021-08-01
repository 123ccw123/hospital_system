package com.ccw.controller;


import com.ccw.commons.MessageConstant;
import com.ccw.commons.PageResult;
import com.ccw.commons.QueryPageBean;
import com.ccw.commons.Result;
import com.ccw.entity.Checkitem;
import com.ccw.service.CheckitemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {

    /*添加检查*/
    @Reference
    private CheckitemService checkitemService;
    @RequestMapping("/save")
    public Result save(@RequestBody Checkitem checkitem){
        boolean b = checkitemService.save(checkitem);
        if(b) {
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }else {
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /*分页*/
    @RequestMapping("/page")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//配置权限
    public Result page(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult result = checkitemService.findByPage(queryPageBean);
            return new Result(true,result);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    /*根据id删除*/
    @RequestMapping("/delete")
    public Result deleteById(Integer id){
        boolean b = checkitemService.removeById(id);
        if(b) {
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }else {
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }
    /*编辑*/
    @RequestMapping("/show")
    public Result show(Integer id){
        Checkitem checkitem = checkitemService.getById(id);
        if(ObjectUtils.isEmpty(checkitem)){
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }else {
            return new Result(true,checkitem);
        }
    }
    /*根据id更新编辑项*/
    @RequestMapping("/update")
    public Result update(@RequestBody Checkitem checkitem){
        boolean b = checkitemService.updateById(checkitem);
        if(b) {
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }else {
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }
    /*创建所有检查信息*/
    @RequestMapping("/list")
    public Result list(){
        List<Checkitem> list = checkitemService.list();
        if(CollectionUtils.isEmpty(list)){
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }else {
            return new Result(true,list);
        }
    }
    @RequestMapping("/showByGroupId")
    @Secured("ROLE_ADMIN")
    public Result showByGroupId(int gid){
        try {
            List<Integer> list = checkitemService.findByCheckGroupId(gid);
            return new Result(true,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}


