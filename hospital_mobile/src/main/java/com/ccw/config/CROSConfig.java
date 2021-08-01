package com.ccw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CROSConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//所有请求地址都通过
                .allowedOriginPatterns("*")//处理所有的请求ip
                .allowedHeaders("*")//处理所有的请求头
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")//同意所有的方法
                .maxAge(3600)//设置有效期
                .allowCredentials(true);//启动跨域请求
    }
}
