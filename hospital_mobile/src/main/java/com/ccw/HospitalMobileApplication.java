package com.ccw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HospitalMobileApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalMobileApplication.class,args);
    }
}
