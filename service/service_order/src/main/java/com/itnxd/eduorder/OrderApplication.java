package com.itnxd.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ITNXD
 * @create 2021-11-15 10:18
 */
@SpringBootApplication
@EnableFeignClients // 远程调用
@EnableDiscoveryClient // 服务发现
@MapperScan("com.itnxd.eduorder.mapper")
@ComponentScan(basePackages = {"com.itnxd"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
