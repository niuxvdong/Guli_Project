package com.itnxd.eduservice.controller;


import com.itnxd.commonutils.R;
import com.itnxd.eduservice.entity.EduChapter;
import com.itnxd.eduservice.entity.chapter.ChapterVo;
import com.itnxd.eduservice.service.EduChapterService;
import org.apache.http.conn.util.PublicSuffixList;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    // 获取章节列表
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allCharapterVideo", list);
    }

    // 章节添加
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    // 根据章节id查章节
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    // 章节修改
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    // 章节删除(若有小结需要将小结删除完毕后才能删除张章节)
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        return flag ? R.ok() : R.error();
    }

}

