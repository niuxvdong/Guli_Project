package com.itnxd.eduservice.mapper;

import com.itnxd.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itnxd.eduservice.entity.frontvo.CourseWebVo;
import com.itnxd.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    // 根据课程id获取发布需要会显得的信息
    public CoursePublishVo getCoursePublishVo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
