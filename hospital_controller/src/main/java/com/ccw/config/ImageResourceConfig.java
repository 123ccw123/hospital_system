package com.ccw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*网络地址与硬盘上的文件相匹配，采用网络地址的方法实现对图片的访问*/
@Configuration
public class ImageResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //定义请求地址；资源存放的路径
        registry.addResourceHandler("/setmeal/**").addResourceLocations("file:C:\\Users\\ccw\\Desktop\\Java\\upload\\");
    }
}
