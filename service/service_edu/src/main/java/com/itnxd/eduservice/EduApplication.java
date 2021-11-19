package com.itnxd.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ITNXD
 * @create 2021-11-02 19:58
 */
@SpringBootApplication
@EnableDiscoveryClient // 开启nacos服务注册
@EnableFeignClients // 开启OpenFeign服务调用
@ComponentScan(basePackages = {"com.itnxd"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
