package com.byd.msp.requirement.common.utils;

import com.byd.msp.requirement.common.config.MinioConfig;
import com.byd.msp.requirement.common.enums.CustomResultTypeEnum;
import com.byd.msp.requirement.common.exception.BizException;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
public class MinioUtil {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    /**
     * 上传文件，返回 objectKey
     */
    public String upload(MultipartFile file) {
        String objectKey = generateObjectKey(file.getOriginalFilename());
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectKey)
                    .stream(is, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            log.info("文件上传成功: {}", objectKey);
            return objectKey;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new BizException(CustomResultTypeEnum.ERROR, "文件上传失败");
        }
    }

    /**
     * 下载文件
     */
    public InputStream download(String objectKey) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectKey)
                    .build());
        } catch (Exception e) {
            log.error("文件下载失败: {}", objectKey, e);
            throw new BizException(CustomResultTypeEnum.ERROR, "文件下载失败");
        }
    }

    /**
     * 获取预览 URL（有效期 1 小时）
     */
    public String preview(String objectKey) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectKey)
                    .expiry(3600)
                    .build());
        } catch (Exception e) {
            log.error("获取预览链接失败: {}", objectKey, e);
            throw new BizException(CustomResultTypeEnum.ERROR, "获取预览链接失败");
        }
    }

    /**
     * 删除文件
     */
    public void delete(String objectKey) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(objectKey)
                    .build());
        } catch (Exception e) {
            log.error("文件删除失败: {}", objectKey, e);
            throw new BizException(CustomResultTypeEnum.ERROR, "文件删除失败");
        }
    }

    private String generateObjectKey(String originalName) {
        String ext = "";
        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf("."));
        }
        return UUID.randomUUID().toString().replace("-", "") + ext;
    }
}
