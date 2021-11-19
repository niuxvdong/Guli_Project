package com.itnxd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itnxd.eduservice.entity.EduChapter;
import com.itnxd.eduservice.entity.EduVideo;
import com.itnxd.eduservice.entity.chapter.ChapterVo;
import com.itnxd.eduservice.entity.chapter.VideoVo;
import com.itnxd.eduservice.mapper.EduChapterMapper;
import com.itnxd.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itnxd.eduservice.service.EduVideoService;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    // 获取课程大纲列表
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //最终要的到的数据列表
        ArrayList<ChapterVo> chapterVoArrayList = new ArrayList<>();

        //获取章节信息
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChaptersList = baseMapper.selectList(wrapperChapter);

        //获取课时信息
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideosList = videoService.list(wrapperVideo);

        //填充章节vo数据
        for (int i = 0; i < eduChaptersList.size(); i++) {
            EduChapter chapter = eduChaptersList.get(i);
            //创建章节vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoArrayList.add(chapterVo);

            //填充课时vo数据
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();
            for (int j = 0; j < eduVideosList.size(); j++) {
                EduVideo video = eduVideosList.get(j);
                if(chapter.getId().equals(video.getChapterId())){
                    //创建课时vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoArrayList);
        }
        System.out.println("===================" + chapterVoArrayList);
        return chapterVoArrayList;
    }

    // 根据章节id删除章节 (若有小结需要将小结删除完毕后才能删除张章节)
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        // 没有小结
        if(count == 0){
            int res = baseMapper.deleteById(chapterId);
            return res > 0;
        }else{
            // 有小结
            throw new GuliException(20001, "有小结，不能删除！");
        }
    }

    // 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
