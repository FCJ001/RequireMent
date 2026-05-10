package com.byd.msp.requirement.common.exception;

import com.byd.msp.requirement.common.result.ResultType;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ResultType resultType;
    private final String msg;

    public BaseException(ResultType resultType) {
        super(resultType.getMsg());
        this.resultType = resultType;
        this.msg = resultType.getMsg();
    }

    public BaseException(ResultType resultType, String msg) {
        super(msg);
        this.resultType = resultType;
        this.msg = msg;
    }
}
