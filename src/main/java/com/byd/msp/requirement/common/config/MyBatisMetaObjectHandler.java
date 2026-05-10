package com.byd.msp.requirement.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String userId = getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createdBy", String.class, userId);
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updatedBy", String.class, userId);
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updatedBy", String.class, userId);
        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, now);
    }

    private String getCurrentUserId() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String userId = request.getHeader("userId");
                if (userId != null) {
                    return userId;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return "system";
    }
}
