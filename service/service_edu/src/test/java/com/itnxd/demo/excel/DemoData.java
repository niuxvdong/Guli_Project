package com.itnxd.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ITNXD
 * @create 2021-11-06 10:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoData {
    @ExcelProperty(value = "学号", index = 0)
    private String sno;
    @ExcelProperty(value = "姓名", index = 1)
    private String name;
}
