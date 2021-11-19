package com.itnxd.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ITNXD
 * @create 2021-11-16 21:47
 */
@Component
@FeignClient(name = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient {

    // 根据用户id和课程id获取订单表中订单的状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);
}
