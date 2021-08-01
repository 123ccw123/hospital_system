package com.ccw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ccw.mapper")
public class HospitalProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalProviderApplication.class,args);
    }
}
