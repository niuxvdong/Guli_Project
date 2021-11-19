package com.itnxd.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itnxd.eduservice.entity.EduSubject;
import com.itnxd.eduservice.entity.excel.SubjectData;
import com.itnxd.eduservice.entity.subject.OneSubject;
import com.itnxd.eduservice.entity.subject.TwoSubject;
import com.itnxd.eduservice.listener.SubjectExcelListener;
import com.itnxd.eduservice.mapper.EduSubjectMapper;
import com.itnxd.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    // 读取后台上传的excel数据
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20002, "添加课程分类失败！");
        }
    }

    // 获取所有的一级和二级分类
    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        // 1. 查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);

        // 2. 查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);

        // 3. 封装为树形结构
        List<OneSubject> finalSubjectList = new ArrayList<>();

        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);

            // 一级分类集合获取到单一一级分类 组合二级分类 添加到 finalSubjectList
            OneSubject oneSubject = new OneSubject();
            /*oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());*/
            // 简写 利用spring工具类 将左边按照右边的需求进行赋值
            BeanUtils.copyProperties(eduSubject, oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject subSubject = twoSubjectList.get(j);
                // 相等则说明这个二级分类是一级分类的子分类
                if(oneSubject.getId().equals(subSubject.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            // 一级分类添加对应的二级分类
            oneSubject.setChildren(twoFinalSubjectList);
            // 最终：树形结构的一级分类添加
            finalSubjectList.add(oneSubject);
        }
        return finalSubjectList;
    }
}
