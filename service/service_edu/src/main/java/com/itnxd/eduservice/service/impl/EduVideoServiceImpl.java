package com.itnxd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itnxd.eduservice.client.VodClient;
import com.itnxd.eduservice.entity.EduVideo;
import com.itnxd.eduservice.mapper.EduVideoMapper;
import com.itnxd.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    // 根据课程id删除小节
    // TODO 待完善，还需要删除视频
    @Override
    public void removeVideoByCourseId(String courseId) {

        // 根据课程id查询所有视频id
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        videoWrapper.select("video_source_id"); // 指定要查询的列
        List<EduVideo> eduVideoList = baseMapper.selectList(videoWrapper);

        // List<EduVideo> 转化为 List<String> 流式编程
        List<String> videoIds = eduVideoList.stream()
                .map(EduVideo::getVideoSourceId) // 只映射videoSourceId
                .filter(x -> !StringUtils.isEmpty(x)) // videoSourceId 非空过滤
                .collect(Collectors.toList()); // 转化为list集合
        if(videoIds.size() > 0){
            vodClient.deleteBatch(videoIds);
        }

        // 删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
