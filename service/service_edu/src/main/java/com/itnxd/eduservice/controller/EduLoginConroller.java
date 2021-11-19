package com.itnxd.eduservice.controller;

import com.itnxd.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * 跨域解决：
 *  1. @CrossOrigin
 *  2. SpringCloud 的 网关
 *
 * @author ITNXD
 * @create 2021-11-04 17:04
 */
@CrossOrigin // 解决跨域请求
//@RestController
@RequestMapping("/eduservice/user")
public class EduLoginConroller {

    @PostMapping("/login")
    public R login(){
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://cdn.jsdelivr.net/gh/niuxvdong/pic@a1e13605cda2153492dd78d3e8f6dca4cdcd497e/2021/11/04/701c3fdbd20a6da283ccfd9d48953860.png");
    }
}
