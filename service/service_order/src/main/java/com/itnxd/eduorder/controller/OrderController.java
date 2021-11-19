package com.itnxd.eduorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itnxd.commonutils.JwtUtils;
import com.itnxd.commonutils.R;
import com.itnxd.eduorder.entity.Order;
import com.itnxd.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-15
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        // 创建订单返回订单号
        String orderNo = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderNo);
    }

    // 根据订单号查询订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo (@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    // 根据用户id和课程id获取订单表中订单的状态
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId){

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId).eq("member_id", memberId)
                .eq("status", 1);
        int count = orderService.count(wrapper);
        return count != 0;
    }
}

