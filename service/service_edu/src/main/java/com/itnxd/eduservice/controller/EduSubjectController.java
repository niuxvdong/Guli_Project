package com.itnxd.eduservice.controller;


import com.itnxd.commonutils.R;
import com.itnxd.eduservice.entity.excel.SubjectData;
import com.itnxd.eduservice.entity.subject.OneSubject;
import com.itnxd.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
@RestController
//@CrossOrigin // 解决跨域
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类
    // 获取上传过来的文件进行读取
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    // 课程列表分类（树形分类）
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }

}



