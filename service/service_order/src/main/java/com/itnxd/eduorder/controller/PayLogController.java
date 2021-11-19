package com.itnxd.eduorder.controller;


import com.itnxd.commonutils.R;
import com.itnxd.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-15
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduorder/paylog")
public class PayLogController {

    @Autowired
    PayLogService payLogService;

    // 根据订单号生成二维码接口
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        // 返回信息包含二维码地址和其他信息
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    // 查询订单支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map map = payLogService.queryPayStatus(orderNo);
        if(map == null){
            return R.error().message("支付出错了");
        }
        // 从map中取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {
            //如果成功 添加记录到支付表 更改订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        // 该状态码是前端response拦截器规定好的，表示不进行处理，等待支付
        return R.ok().code(25000).message("支付中");
    }

}

