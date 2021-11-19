package com.itnxd.msmservice.controller;

import com.itnxd.commonutils.R;
import com.itnxd.commonutils.RandomUtil;
import com.itnxd.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author ITNXD
 * @create 2021-11-10 14:14
 */
@RestController
//@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 发送验证码 默认五分钟有效
    @GetMapping(value = "/send/{phone}")
    public R code(@PathVariable String phone) {
        // 1. redis中有验证码说明验证码五分钟还没有失效，直接返回ok
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        // 2. 否则说明验证码失效，生成随机的六位验证码
        code = RandomUtil.getSixBitRandom();
        // Map<String,Object> param = new HashMap<>();
        // param.put("code", code);
        boolean isSend = msmService.send(phone, code);
        if (isSend) {
            // 发送成功，在redis中设置验证码为五分钟有效
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }
}
