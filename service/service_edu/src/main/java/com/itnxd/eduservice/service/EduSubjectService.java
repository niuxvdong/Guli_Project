package com.itnxd.eduservice.service;

import com.itnxd.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itnxd.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
