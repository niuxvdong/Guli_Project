package com.itnxd.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ITNXD
 * @create 2021-11-10 22:16
 */
@ComponentScan(basePackages = {"com.itnxd"})
@EnableDiscoveryClient // 服务发现
@MapperScan("com.itnxd.educenter.mapper")
@SpringBootApplication
public class UcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
