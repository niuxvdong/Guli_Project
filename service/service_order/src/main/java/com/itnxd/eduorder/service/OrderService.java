package com.itnxd.eduorder.service;

import com.itnxd.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-15
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberIdByJwtToken);
}
