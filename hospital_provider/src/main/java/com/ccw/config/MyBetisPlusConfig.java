package com.ccw.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MyBetisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor();
        innerInterceptor.setDbType(DbType.MYSQL);
        innerInterceptor.setOverflow(true);
        mybatisPlusInterceptor.addInnerInterceptor(innerInterceptor);
        return mybatisPlusInterceptor;
    }
    /*public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        for (int i = 0; i < nums.length-1; i++) {
            for (int j = 1; j < nums.length-1; j++) {
                if (nums[i] + nums[j] == target) {
                    int[] result = {nums[i], nums[j]};
                    System.out.println(Arrays.toString(result));
                }
            }
        }
    }*/
    /*public double findMedianSortedArrays() {
        int[] nums1={1,3,5};
        int[] nums2={2,4,6};
        int length = nums1.length+nums2.length;
        int[] nums3 = new int[length];
        return 10.5;
    }*/
}
