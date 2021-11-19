package com.itnxd.oss.controller;

import com.itnxd.commonutils.R;
import com.itnxd.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ITNXD
 * @create 2021-11-05 20:52
 */
@RestController
//@CrossOrigin // 解决跨域
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传头像方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url", url);
    }

}
