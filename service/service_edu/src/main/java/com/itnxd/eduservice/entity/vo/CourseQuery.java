package com.itnxd.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ITNXD
 * @create 2021-11-08 13:29
 */
@Data
public class CourseQuery {

    // 课程名称
    @ApiModelProperty(value = "课程名称,模糊查询")
    private String title;
    // 课程状态
    @ApiModelProperty(value = "教师状态, Normal Draft")
    private String status;
}
