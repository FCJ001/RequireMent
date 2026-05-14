package com.byd.msp.requirement.common.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelImportListener<T> implements ReadListener<T> {

    @Getter
    private final List<T> dataList = new ArrayList<>();

    @Override
    public void invoke(T data, AnalysisContext context) {
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("Excel 读取完成，共 {} 条", dataList.size());
    }

    @Override
    public void onException(Exception e, AnalysisContext context) {
        log.error("Excel 解析异常: 行={}", context.readRowHolder().getRowIndex(), e);
        throw new RuntimeException("Excel 解析失败，第 " + context.readRowHolder().getRowIndex() + " 行");
    }
}
