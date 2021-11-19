package com.itnxd.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itnxd.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itnxd.eduservice.entity.frontvo.CourseFrontVo;
import com.itnxd.eduservice.entity.frontvo.CourseWebVo;
import com.itnxd.eduservice.entity.vo.CourseInfoVo;
import com.itnxd.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
