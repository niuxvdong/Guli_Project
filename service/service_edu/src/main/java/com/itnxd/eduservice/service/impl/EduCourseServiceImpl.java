package com.itnxd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itnxd.eduservice.entity.EduCourse;
import com.itnxd.eduservice.entity.EduCourseDescription;
import com.itnxd.eduservice.entity.frontvo.CourseFrontVo;
import com.itnxd.eduservice.entity.frontvo.CourseWebVo;
import com.itnxd.eduservice.entity.vo.CourseInfoVo;
import com.itnxd.eduservice.entity.vo.CoursePublishVo;
import com.itnxd.eduservice.mapper.EduCourseMapper;
import com.itnxd.eduservice.service.EduChapterService;
import com.itnxd.eduservice.service.EduCourseDescriptionService;
import com.itnxd.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itnxd.eduservice.service.EduVideoService;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    // 注入描述表的service
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    // 添加课程基本信息（向两张表添加信息 课程表 课程描述表）
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        // 将 courseInfoVo 分离为 eduCourse 和 eduCourseDescription
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if(insert <= 0){
            throw new GuliException(20001, "添加课程信息失败！");
        }

        // 获取添加课程成功的id
        String cid = eduCourse.getId();

        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    // 点击上一步时，进行数据回显查询方法
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        // 查询课程表和描述表
        EduCourse course = baseMapper.selectById(courseId);
        EduCourseDescription description = courseDescriptionService.getById(courseId);

        // 封装数据
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);
        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }

    // 更新课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if(update == 0){
            throw new GuliException(20001, "修改课程失败！");
        }

        // 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        courseDescriptionService.updateById(description);
    }

    // 根据课程id查询课程
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        return baseMapper.getCoursePublishVo(id);
    }

    // 删除课程
    @Override
    public void removeCourse(String courseId) {

        // 删除小节
        videoService.removeVideoByCourseId(courseId);
        // 删除章节
        chapterService.removeChapterByCourseId(courseId);
        // 删除描述
        courseDescriptionService.removeById(courseId);
        // 删除课程
        int delete = baseMapper.deleteById(courseId);

        if(delete == 0){
            throw new GuliException(20001, "删除章节失败！");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage,
                                                  CourseFrontVo courseFrontVo) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, wrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
