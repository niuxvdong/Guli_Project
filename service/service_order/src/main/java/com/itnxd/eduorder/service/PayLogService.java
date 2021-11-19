package com.itnxd.eduorder.service;

import com.itnxd.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-15
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);

    Map queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
