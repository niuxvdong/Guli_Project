package com.itnxd.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itnxd.eduservice.entity.EduSubject;
import com.itnxd.eduservice.entity.excel.SubjectData;
import com.itnxd.eduservice.service.EduSubjectService;
import com.itnxd.servicebase.exceptionhandler.GuliException;

import java.util.Map;

/**
 * @author ITNXD
 * @create 2021-11-06 10:31
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // SubjectExcelListener不能交给spring管理，需要自己传入EduSubjectService
    // 因此将subjectService从controller传入service传入SubjectExcelListener构造器
    public EduSubjectService subjectService;

    public SubjectExcelListener() {}
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 按行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001, "excel文件数据为空！");
        }
        // 按行读取，第一个值一级分类，第二个值二级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(existOneSubject == null){
            // 没有相同一级分类则进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        // 获取二级分类对应的父id
        String pid = existOneSubject.getId();

        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null){
            // 没有相同二级分类则进行添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    // 判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService, String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return subjectService.getOne(wrapper);
    }

    // 判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }

    // 表头读取
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    // 全部读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
