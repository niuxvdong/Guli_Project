package com.itnxd.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.itnxd.commonutils.R;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import com.itnxd.vod.service.VodService;
import com.itnxd.vod.utils.ConstantVodUtils;
import com.itnxd.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ITNXD
 * @create 2021-11-08 18:25
 */
@RestController
//@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    // 根据id删除一个视频
    @DeleteMapping("/removeVideo/{id}")
    public R removeVideo(@PathVariable String id){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            // 创建删除视频的 request
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 设置视频id
            request.setVideoIds(id);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败！");
        }
        return R.ok();
    }

    // 删除多个视频的方法 通过id的list删除
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeVideoByList(videoIdList);
        return R.ok();
    }

    // 根据视频id获取播放凭证
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            // 根据视频id获取视频凭证（加不加密都有）
            // 创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            // 创建获取视频凭证的request和response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            // 向request设置视频id
            request.setVideoId(id);

            // 发送request的到response
            response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
