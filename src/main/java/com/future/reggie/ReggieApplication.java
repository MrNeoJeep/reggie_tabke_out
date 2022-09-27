package com.future.reggie;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableCaching
public class ReggieApplication {
//    static {
//        System.setProperty("druid.mysql.usePingMethod","false");
//    }
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("this project starts now!");
    }
}
