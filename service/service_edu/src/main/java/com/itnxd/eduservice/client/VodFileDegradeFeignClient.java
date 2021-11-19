package com.itnxd.eduservice.client;

import com.itnxd.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务降级，远程调用出错执行！
 * @author ITNXD
 * @create 2021-11-09 15:54
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R removeVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除视频出错了");
    }
}
