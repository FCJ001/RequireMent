package com.byd.msp.requirement.common.exception;

import com.byd.msp.requirement.common.result.ResultType;

public class BizException extends BaseException {

    public BizException(ResultType resultType) {
        super(resultType);
    }

    public BizException(ResultType resultType, String msg) {
        super(resultType, msg);
    }
}
