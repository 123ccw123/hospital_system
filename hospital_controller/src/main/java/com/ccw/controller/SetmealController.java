package com.ccw.controller;


import com.ccw.commons.*;
import com.ccw.entity.Checkgroup;
import com.ccw.entity.Setmeal;
import com.ccw.service.SetmealService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-07-05
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile multipartFile){
        //获取文件上传名称并重命名
        String name = UUID.randomUUID().toString() + multipartFile.getOriginalFilename();
        File f = new File("C:\\Users\\ccw\\Desktop\\Java\\upload\\"+name);
        try {
            multipartFile.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        //将上传图片保存到Redis中
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_UPLOAD,name);
        return new Result(true,MessageConstant.UPLOAD_SUCCESS,name);
    }
    /*提交检查组信息*/
    @RequestMapping("/save")
    public Result save(Integer[] checkgroupIds, @RequestBody Setmeal setmeal){
        try {
            setmealService.save(checkgroupIds,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        //将上传数据存储到数据库中
        redisTemplate.opsForSet().add(RedisConstant.SETMEAL_PIC_DB,setmeal.getImg());
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    /*获取列表全部信息,以及分页查询的实现*/
    @RequestMapping("/page")
    public Result page(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult result = setmealService.findPageById(queryPageBean);
            return new Result(true,result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/delete")
    public Result delete(int id){
        try {
            setmealService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/show")
    public Result show(int id){
        Setmeal setmeal = setmealService.getById(id);
        if(!ObjectUtils.isEmpty(setmeal)){
            return new Result(true,setmeal);
        }else {
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    @RequestMapping("/list")
    public Result list(){
        List<Setmeal> list = setmealService.list();
        if (CollectionUtils.isEmpty(list)){
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }else {
            return new Result(true,list);
        }
    }
    /*双表联合查询得出套餐组中的选项*/
    @RequestMapping("/showBySetmealId")
    public Result showByGroupId(int sid){
        try {
            List<Integer> list = setmealService.findByCheckGroupId(sid);
            return new Result(true,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
    /*编辑页面*/
    @RequestMapping("/update")
    public Result update(Integer[] checkgroupIds,@RequestBody Setmeal setmeal ){
        try {
            setmealService.editUpdate(checkgroupIds,setmeal);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

}


