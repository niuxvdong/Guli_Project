package com.itnxd.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.itnxd.commonutils.R;
import com.itnxd.servicebase.exceptionhandler.GuliException;
import com.itnxd.vod.service.VodService;
import com.itnxd.vod.utils.ConstantVodUtils;
import com.itnxd.vod.utils.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ITNXD
 * @create 2021-11-08 18:26
 */
@Service
public class VodServiceImpl implements VodService {

    // 上传视频
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = "";
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 根据 videoIdList 删除一个课程中的所有视频
    @Override
    public void removeVideoByList(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            // 创建删除视频的 request
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 设置视频id 将list分割为 1,2,3 使用流式编程即可
            String videoIds = String.join(",", videoIdList);

            request.setVideoIds(videoIds);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败！");
        }
    }
}
