package com.byd.msp.requirement.common.exception;

import com.byd.msp.requirement.common.result.NullSerializable;
import com.byd.msp.requirement.common.result.GlobalResultTypeEnum;
import com.byd.msp.requirement.common.result.Result;
import com.byd.msp.requirement.common.utils.I18nMessageCommonUtil;
import com.byd.msp.requirement.common.enums.CustomResultTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result<NullSerializable> businessExceptionHandler(HttpServletResponse response, BizException ex) {
        log.error("BizException, ex:{}", ex.getMessage(), ex);
        return Result.fail(ex.getResultType(), I18nMessageCommonUtil.getLocale(ex.getMsg()));
    }

    @ExceptionHandler(BaseException.class)
    Result<?> handleBusinessException(HttpServletResponse response, BaseException be) {
        log.error("BaseException, ex:{}", be.getMessage(), be);
        return GlobalResultTypeEnum.ERROR.getCode().equals(be.getResultType().getCode()) ? Result.fail() : Result.fail(be.getResultType(),
                I18nMessageCommonUtil.getLocale(be.getResultType().getMsg()));
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<NullSerializable> handleRuntimeException(HttpServletResponse response, RuntimeException e) {
        log.error("RuntimeException, ex:{}", e.getMessage(), e);
        return Result.fail(CustomResultTypeEnum.ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .filter(Objects::nonNull)
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));
        log.warn(errorMessage);
        return Result.fail(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.warn("Spring参数校验异常：" + message, e);
        return Result.fail(message);
    }

    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        String message = "请求参数" + Objects.requireNonNull(e.getFieldError()).getField() + "有误";
        log.error(message, e);
        return Result.fail(message);
    }
}
