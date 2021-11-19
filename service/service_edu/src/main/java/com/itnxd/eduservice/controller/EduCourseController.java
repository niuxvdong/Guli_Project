package com.itnxd.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itnxd.commonutils.R;
import com.itnxd.eduservice.entity.EduCourse;
import com.itnxd.eduservice.entity.EduTeacher;
import com.itnxd.eduservice.entity.vo.CourseInfoVo;
import com.itnxd.eduservice.entity.vo.CoursePublishVo;
import com.itnxd.eduservice.entity.vo.CourseQuery;
import com.itnxd.eduservice.entity.vo.TeacherQuery;
import com.itnxd.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    EduCourseService courseService;

    // 完善条件查询带分页功能
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R getCourseList(@PathVariable("current") long current,
                           @PathVariable("limit") long limit,
                           // 将json封装为对象（只能post使用）
                           @RequestBody(required = false) CourseQuery courseQuery){

        Page<EduCourse> pageCourse = new Page<>(current, limit);

        // 条件构造
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        // 根据更新时间降序排列
        wrapper.orderByDesc("gmt_modified");

        courseService.page(pageCourse, wrapper);

        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    // 添加课程基本信息
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    // 根据课程id查询课程信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 根据课程id查询课程信息
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    // 课程最终发布 只需修改课程
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        System.out.println("test");
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal"); // 已发布状态为 Normal
        courseService.updateById(course);
        return R.ok();
    }

    // 删除课程
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

