package com.itnxd.eduservice.client;

import com.itnxd.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ITNXD
 * @create 2021-11-09 10:29
 */
// 要调用的服务 以及兜底方法的实现类
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    // 定义调用服务地址（从vod中复制过来改为全路径即可）
    // 注意：PathVariable注解要指定路径参数名称
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public R removeVideo(@PathVariable("id") String id);

    // 删除多个视频的方法 通过id的list删除
    @DeleteMapping("/eduvod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
