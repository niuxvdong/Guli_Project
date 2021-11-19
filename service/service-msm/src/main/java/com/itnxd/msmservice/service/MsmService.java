package com.itnxd.msmservice.service;

import java.util.Map;

/**
 * @author ITNXD
 * @create 2021-11-10 14:17
 */
public interface MsmService {
    boolean send(String phone, String code);
}
