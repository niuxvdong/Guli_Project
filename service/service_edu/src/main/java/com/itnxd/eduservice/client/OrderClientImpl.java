package com.itnxd.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * @author ITNXD
 * @create 2021-11-16 21:48
 */
@Component
public class OrderClientImpl implements OrderClient{

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
