package com.byd.msp.requirement.common.controller;

import com.byd.msp.requirement.common.result.Result;
import com.byd.msp.requirement.common.utils.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
@Api(tags = "文件管理")
public class FileController {

    @Resource
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        String objectKey = minioUtil.upload(file);
        return Result.success(objectKey);
    }

    @GetMapping("/download/{objectKey}")
    @ApiOperation("文件下载")
    public void download(@PathVariable String objectKey, HttpServletResponse response) {
        try (InputStream is = minioUtil.download(objectKey)) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(objectKey, "UTF-8"));
            ServletOutputStream os = response.getOutputStream();
            byte[] buffer = new byte[8192];
            int len;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }

    @GetMapping("/preview/{objectKey}")
    @ApiOperation("文件预览")
    public Result<String> preview(@PathVariable String objectKey) {
        String url = minioUtil.preview(objectKey);
        return Result.success(url);
    }

    @DeleteMapping("/delete/{objectKey}")
    @ApiOperation("文件删除")
    public Result<Void> delete(@PathVariable String objectKey) {
        minioUtil.delete(objectKey);
        return Result.success();
    }
}
