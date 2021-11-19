package com.itnxd.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ITNXD
 * @create 2021-11-08 18:26
 */

public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeVideoByList(List<String> videoIdList);
}
