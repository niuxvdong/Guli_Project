package com.itnxd.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ITNXD
 * @create 2021-11-06 10:03
 */
public class TestExcel {

    public static void main(String[] args) {

        String fileName = "E:\\test.xlsx";
        // 写操作
        /*EasyExcel.write(fileName, DemoData.class).sheet("学生信息")
                .doWrite(getData());*/

        // 读操作
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    public static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new DemoData("" + i, "test" + i));
        }
        return list;
    }
}
