package com.itnxd.eduservice.entity.vo;

import lombok.Data;

/**
 * @author ITNXD
 * @create 2021-11-08 10:15
 */
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
