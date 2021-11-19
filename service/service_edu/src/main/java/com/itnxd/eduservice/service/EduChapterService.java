package com.itnxd.eduservice.service;

import com.itnxd.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itnxd.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
