package com.itnxd.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ITNXD
 * @create 2021-11-09 18:07
 */
@SpringBootApplication
@ComponentScan({"com.itnxd"}) //指定扫描位置
@MapperScan("com.itnxd.educms.mapper")
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
