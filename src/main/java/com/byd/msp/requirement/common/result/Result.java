package com.byd.msp.requirement.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(GlobalResultTypeEnum.SUCCESS.getCode(), GlobalResultTypeEnum.SUCCESS.getMsg(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(GlobalResultTypeEnum.SUCCESS.getCode(), GlobalResultTypeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(GlobalResultTypeEnum.ERROR.getCode(), GlobalResultTypeEnum.ERROR.getMsg(), null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(GlobalResultTypeEnum.ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> fail(String code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> fail(ResultType resultType) {
        return new Result<>(resultType.getCode(), resultType.getMsg(), null);
    }

    public static <T> Result<T> fail(ResultType resultType, String msg) {
        return new Result<>(resultType.getCode(), msg, null);
    }
}
