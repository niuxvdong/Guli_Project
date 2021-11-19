package com.itnxd.eduservice.controller;


import com.itnxd.commonutils.R;
import com.itnxd.eduservice.client.VodClient;
import com.itnxd.eduservice.entity.EduVideo;
import com.itnxd.eduservice.service.EduVideoService;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author ITNXD
 * @since 2021-11-06
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    // 注入VodClient
    @Autowired
    private VodClient vodClient;

    // 添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo video){
        videoService.save(video);
        return R.ok();
    }

    // 删除小节 以及阿里云视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){

        // 根据小节id得到视频id，调用vodClient删除阿里云视频
        EduVideo video = videoService.getById(id);
        System.out.println("==================" + video.getVideoSourceId());

        if(!StringUtils.isEmpty(video.getVideoSourceId())){
            R r = vodClient.removeVideo(video.getVideoSourceId());
            if(r.getCode() == 20001){
                throw new GuliException(20001, "删除视频失败，执行了降级处理！");
            }
        }

        // 删除小结
        videoService.removeById(id);
        return R.ok();
    }

    // 修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        videoService.updateById(video);
        return R.ok();
    }

    // 根据id查询小节信息
    @GetMapping("/getVideo/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return R.ok().data("video", video);
    }
}

