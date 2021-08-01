package com.ccw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//处理所有的请求url地址 checkitem/save
                .allowedOriginPatterns("*")//处理所有的ip
                .allowedHeaders("*")//处理任意的请求头信息
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")//允许的请求方法
                .maxAge(3600)//设置有效期
                .allowCredentials(true);//启动跨域配置
    }
}
