package com.byd.msp.requirement.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.byd.msp.requirement.common.enums.CustomResultTypeEnum;
import com.byd.msp.requirement.common.exception.BizException;
import com.byd.msp.requirement.common.excel.ExcelImportListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class ExcelUtil {

    /**
     * 导入 Excel（默认读取第一个 sheet）
     *
     * @param file  Excel 文件
     * @param clazz 数据类（需用 @ExcelProperty 标注字段）
     * @return 解析后的数据列表
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) {
        if (file == null || file.isEmpty()) {
            throw new BizException(CustomResultTypeEnum.ERROR, "上传文件不能为空");
        }
        try (InputStream is = file.getInputStream()) {
            return importExcel(is, clazz);
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            log.error("Excel 导入失败: {}", file.getOriginalFilename(), e);
            throw new BizException(CustomResultTypeEnum.ERROR, "Excel解析失败, 请检查文件内容是否正确");
        }
    }

    /**
     * 导入 Excel
     */
    public static <T> List<T> importExcel(InputStream inputStream, Class<T> clazz) {
        ExcelImportListener<T> listener = new ExcelImportListener<>();
        EasyExcel.read(inputStream, clazz, listener).sheet().doRead();
        return listener.getDataList();
    }

    /**
     * 分批次导入（大数据量，避免内存溢出）
     *
     * @param batchSize 每批次条数
     * @param batchConsumer 每批次回调处理
     */
    public static <T> void importExcelBatch(InputStream inputStream, Class<T> clazz,
                                             int batchSize, Consumer<List<T>> batchConsumer) {
        ExcelImportListener<T> listener = new ExcelImportListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                super.invoke(data, context);
                if (getDataList().size() >= batchSize) {
                    batchConsumer.accept(new ArrayList<>(getDataList()));
                    getDataList().clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                if (CollUtil.isNotEmpty(getDataList())) {
                    batchConsumer.accept(new ArrayList<>(getDataList()));
                }
                super.doAfterAllAnalysed(context);
            }
        };
        EasyExcel.read(inputStream, clazz, listener).sheet().doRead();
    }

    /**
     * 导出 Excel（适配列宽）
     *
     * @param response HTTP 响应
     * @param fileName 下载文件名（不含 .xlsx）
     * @param sheetName sheet 名
     * @param dataList 数据列表
     * @param clazz    数据类（需用 @ExcelProperty 标注字段）
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName,
                                        String sheetName, List<T> dataList, Class<T> clazz) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));

            OutputStream os = response.getOutputStream();
            EasyExcel.write(os, clazz)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet(sheetName)
                    .doWrite(dataList);
            os.flush();
        } catch (Exception e) {
            log.error("Excel 导出失败: {}", fileName, e);
            throw new BizException(CustomResultTypeEnum.ERROR, "Excel导出失败");
        }
    }

    /**
     * 导出 Excel 模板（空表头）
     */
    public static void exportTemplate(HttpServletResponse response, String fileName,
                                       String sheetName, Class<?> clazz) {
        exportExcel(response, fileName, sheetName, Collections.emptyList(), (Class<Object>) clazz);
    }
}
