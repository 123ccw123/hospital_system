package com.ccw.controller;


import com.ccw.commons.MessageConstant;
import com.ccw.commons.PageResult;
import com.ccw.commons.QueryPageBean;
import com.ccw.commons.Result;
import com.ccw.entity.Checkgroup;
import com.ccw.service.CheckgroupService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    private CheckgroupService checkgroupService;

/*
    新建表单
*/
    @RequestMapping("/save")
    public Result save(Integer[] checkitemIds, @RequestBody Checkgroup checkgroup){
        if (!(checkitemIds!=null&&checkitemIds.length>0)){
            return new Result(false,MessageConstant.PLEASE_CHECKITEM_FALL);
        }
        try{
            checkgroupService.add(checkitemIds,checkgroup);
            return new Result(true , MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false , MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }
    /*分页查询*/
    @RequestMapping("/page")
    public Result page(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult page = checkgroupService.findByPage(queryPageBean);
            return new Result(true,page);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
    /*显示检查组*/
    @RequestMapping("/show")
    public Result show(int id){
        Checkgroup checkGroup = checkgroupService.getById(id);
        if(!ObjectUtils.isEmpty(checkGroup)){
            return new Result(true,checkGroup);
        }
        return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
    }
    /*编辑检查组*/
    @RequestMapping("/update")
    public Result update(Integer[] checkitemIds, @RequestBody Checkgroup checkgroup ){
        try {
            checkgroupService.update(checkitemIds,checkgroup);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
    /*删除检查组*/
    @RequestMapping("/delete")
    public Result delete(int id){

        try {
            checkgroupService.deleteByGroupId(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
    /*查询检查组的所有信息*/
    @RequestMapping("/list")
    public Result list(){
        List<Checkgroup> list = checkgroupService.list();
        if (CollectionUtils.isEmpty(list)){
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }else{
            return new Result(true,list);
        }
    }
}

