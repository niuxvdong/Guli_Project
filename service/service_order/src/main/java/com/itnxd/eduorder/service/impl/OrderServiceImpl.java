package com.itnxd.eduorder.service.impl;

import com.itnxd.commonutils.CourseWebVoOrder;
import com.itnxd.commonutils.UcenterMemberVo;
import com.itnxd.eduorder.client.EduClient;
import com.itnxd.eduorder.client.UcenterClient;
import com.itnxd.eduorder.entity.Order;
import com.itnxd.eduorder.mapper.OrderMapper;
import com.itnxd.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itnxd.eduorder.utils.OrderNoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-15
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        // 通过远程调用获取到用户信息
        UcenterMemberVo ucenterMemberVo = ucenterClient.getMemberInfoById(courseId);
        // 通过远程调用获取到课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        Order order = new Order();
        // 生成唯一的订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMemberVo.getMobile());
        order.setNickname(ucenterMemberVo.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
