package com.itnxd.staservice.controller;


import com.itnxd.commonutils.R;
import com.itnxd.staservice.client.UcenterClient;
import com.itnxd.staservice.service.StatisticsDailyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-17
 */
@RestController
//@CrossOrigin
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    // 统计某一天注册人数，放入统计表
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day){
        dailyService.registerCount(day);
        return R.ok();
    }

    // 图表显示，返回x轴和y轴，分别为日期JSON数组和数量JSON数组
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end){

        Map<String, Object> map = dailyService.getShowData(type, begin, end);
        return R.ok().data(map);
    }
}

