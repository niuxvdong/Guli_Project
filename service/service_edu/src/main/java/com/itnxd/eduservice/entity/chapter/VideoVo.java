package com.itnxd.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ITNXD
 * @create 2021-11-07 14:14
 */
@ApiModel(value = "课时信息")
@Data
public class VideoVo {

    private String id;
    private String title;
    private Boolean free;

    private String videoSourceId;
}
