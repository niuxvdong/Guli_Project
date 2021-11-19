package com.itnxd.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ITNXD
 * @create 2021-11-05 20:53
 */
public interface OssService {

    // 上传头像到oss
    String uploadAvatar(MultipartFile file);
}
