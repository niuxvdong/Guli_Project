package com.itnxd.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author ITNXD
 * @create 2021-11-06 10:05
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    // 按行读取
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("======" + demoData);
    }

    // 读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("======" + headMap);
    }

    // 全部读完后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
