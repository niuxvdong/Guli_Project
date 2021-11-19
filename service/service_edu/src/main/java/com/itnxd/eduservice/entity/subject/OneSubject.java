package com.itnxd.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 * @author ITNXD
 * @create 2021-11-06 14:31
 */
@Data
public class OneSubject {

    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();
}
