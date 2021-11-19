package com.itnxd.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 封装课程与课程描述新！
 *
 * @author ITNXD
 * @create 2021-11-06 16:24
 */
@Data
@ApiModel(value = "课程基本信息", description = "编辑课程基本信息的表单对象")
public class CourseInfoVo {

    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;
    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;
    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "课程简介")
    private String description;
}
