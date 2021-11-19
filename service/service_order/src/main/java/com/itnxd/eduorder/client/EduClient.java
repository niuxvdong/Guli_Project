package com.itnxd.eduorder.client;

import com.itnxd.commonutils.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ITNXD
 * @create 2021-11-15 15:14
 */
@Component
@FeignClient(value = "service-edu")
public interface EduClient {

    // 根据课程id查询课程信息(订单模块使用)
    @PostMapping("/eduservice/courseFront/getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
