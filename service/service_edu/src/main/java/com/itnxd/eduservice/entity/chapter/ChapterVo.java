package com.itnxd.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ITNXD
 * @create 2021-11-07 14:13
 */
@ApiModel(value = "章节信息")
@Data
public class ChapterVo {

    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
