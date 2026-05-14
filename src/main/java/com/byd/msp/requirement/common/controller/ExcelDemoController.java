package com.byd.msp.requirement.common.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import com.byd.msp.requirement.common.result.Result;
import com.byd.msp.requirement.common.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test/excel")
@Api(tags = "Excel 测试")
public class ExcelDemoController {

    @Data
    public static class DemoData {
        @ExcelProperty("姓名")
        private String name;

        @ExcelProperty("年龄")
        private Integer age;

        @ExcelProperty("邮箱")
        private String email;
    }

    /** 下载模板：GET /test/excel/template */
    @GetMapping("/template")
    @ApiOperation("下载导入模板")
    public void downloadTemplate(HttpServletResponse response) {
        ExcelUtil.exportTemplate(response, "用户导入模板", "Sheet1", DemoData.class);
    }

    /** 导出示例数据：GET /test/excel/export */
    @GetMapping("/export")
    @ApiOperation("导出示例数据")
    public void exportDemo(HttpServletResponse response) {
        List<DemoData> list = new ArrayList<>();
        DemoData d1 = new DemoData();
        d1.setName("张三");
        d1.setAge(28);
        d1.setEmail("zhangsan@test.com");
        list.add(d1);

        DemoData d2 = new DemoData();
        d2.setName("李四");
        d2.setAge(32);
        d2.setEmail("lisi@test.com");
        list.add(d2);

        ExcelUtil.exportExcel(response, "用户数据导出", "Sheet1", list, DemoData.class);
    }

    /** 导入：POST /test/excel/import (form-data, key=file) */
    @PostMapping("/import")
    @ApiOperation("导入Excel文件")
    public Result<List<DemoData>> importExcel(@RequestParam("file") MultipartFile file) {
        List<DemoData> list = ExcelUtil.importExcel(file, DemoData.class);
        return Result.success(list);
    }
}
