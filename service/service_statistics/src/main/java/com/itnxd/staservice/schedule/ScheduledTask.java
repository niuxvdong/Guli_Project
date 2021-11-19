package com.itnxd.staservice.schedule;

import com.itnxd.staservice.service.StatisticsDailyService;
import com.itnxd.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ITNXD
 * @create 2021-11-17 14:02
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService dailyService;

    // 只能写六位
    // @Scheduled(cron = "0/5 * * * * *")
    public void test(){
        System.out.println("=============1");
    }

    // 每天凌晨一点执行，查询前一天数据进行添加
    @Scheduled(cron = "0 0 1 * * *")
    public void test2(){
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }

}
