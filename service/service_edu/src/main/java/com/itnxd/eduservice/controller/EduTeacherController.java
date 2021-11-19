package com.itnxd.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itnxd.commonutils.R;
import com.itnxd.eduservice.entity.EduTeacher;
import com.itnxd.eduservice.entity.vo.TeacherQuery;
import com.itnxd.eduservice.service.EduTeacherService;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-02
 */
@Api(description = "讲师管理")
//@CrossOrigin // 解决跨域问题
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    /**
     * 分页查询讲师！
     *
     * @param current 当前页
     * @param limit   每页条数
     * @return
     */
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,
                             @PathVariable("limit") long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "分页条件查询讲师")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  // 将json封装为对象（只能post使用）
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        // 条件构造
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level) ) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        // 根据更新时间降序排列
        wrapper.orderByDesc("gmt_modified");

        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){
        boolean save = teacherService.save(teacher);
        return save ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据id查讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation(value = "根据id修改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean flag = teacherService.updateById(teacher);
        return flag ? R.ok() : R.error();
    }
}

