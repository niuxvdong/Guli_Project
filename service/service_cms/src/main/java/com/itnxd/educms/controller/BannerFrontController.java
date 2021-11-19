package com.itnxd.educms.controller;

import com.itnxd.commonutils.R;
import com.itnxd.educms.entity.CrmBanner;
import com.itnxd.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ITNXD
 * @create 2021-11-09 18:13
 */
@RestController
//@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    // key双引号里面再加一个单引号
    @Cacheable(key = "'selectIndexList'", value = "banner") // 缓存
    @ApiOperation(value = "获取首页banner")
    @GetMapping("/getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        System.out.println(list);
        return R.ok().data("list", list);
    }

}
