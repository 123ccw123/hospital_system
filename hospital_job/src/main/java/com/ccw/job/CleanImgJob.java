package com.ccw.job;

import com.ccw.commons.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;

@Component
public class CleanImgJob {
    @Autowired
    private RedisTemplate redisTemplate;
    @Scheduled(cron = "0/30 * * * * ?")
    public void clean(){
        System.out.println("打印成功");
        //获取Redis上传的左右图片资源
/*        Set upload = redisTemplate.opsForSet().members(RedisConstant.SETMEAL_PIC_UPLOAD);
        //获取Redis上传到数据库的所有图片资源
        Set db = redisTemplate.opsForSet().members(RedisConstant.SETMEAL_PIC_DB);*/
        Set<String> difference = redisTemplate.opsForSet()
                .difference(RedisConstant.SETMEAL_PIC_UPLOAD, RedisConstant.SETMEAL_PIC_DB);
        for (String s : difference) {
            //获取需要删除的图片并将其删除
            new File("C:\\Users\\ccw\\Desktop\\Java\\upload\\"+s).delete();
        }
    }
}
