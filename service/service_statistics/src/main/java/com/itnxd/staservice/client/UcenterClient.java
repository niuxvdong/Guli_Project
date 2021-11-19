package com.itnxd.staservice.client;

import com.itnxd.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ITNXD
 * @create 2021-11-17 10:44
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    // 查询某一天的注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
