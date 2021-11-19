package com.itnxd.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itnxd.commonutils.R;
import com.itnxd.educms.entity.CrmBanner;
import com.itnxd.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台banner管理接口！
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-09
 */
@RestController
//@CrossOrigin
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "分页Banner")
    @GetMapping("/pageBanner/{current}/{limit}")
    public R pageBanner(@PathVariable("current") long current,
                        @PathVariable("limit") long limit){

        Page<CrmBanner> bannerPage = new Page<>(current, limit);
        bannerService.page(bannerPage, null);
        return R.ok().data("items", bannerPage.getRecords())
                .data("total", bannerPage.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}

