package com.itnxd.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.itnxd.oss.service.OssService;
import com.itnxd.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author ITNXD
 * @create 2021-11-05 20:53
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();

            // 文件名称 唯一性
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            // 添加日期分类 joda-time 依赖的方法获取日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;

            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();

            // 路径示例：https://nxd.oss-cn-beijing.aliyuncs.com/test.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
