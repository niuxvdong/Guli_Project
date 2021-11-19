package com.itnxd.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itnxd.commonutils.R;
import com.itnxd.eduservice.entity.EduCourse;
import com.itnxd.eduservice.entity.EduTeacher;
import com.itnxd.eduservice.service.EduCourseService;
import com.itnxd.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ITNXD
 * @create 2021-11-12 15:33
 */
@CrossOrigin
//@RestController
@RequestMapping("/eduservice/teacherFront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    // 分页查询讲师
    @PostMapping("/getTeacherFrontList/{current}/{limit}")
    public R getTeacherFrontList(@PathVariable long current,
                                 @PathVariable long limit){

        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);
    }

    // 讲师信息
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        // 1. 根据id查询讲师基本信息
        EduTeacher teacher = teacherService.getById(teacherId);
        // 2. 根据讲师id查询讲师所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }


}
