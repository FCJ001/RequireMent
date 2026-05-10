package com.byd.msp.requirement.common.enums;

import com.byd.msp.requirement.common.result.GlobalResultTypeEnum;
import com.byd.msp.requirement.common.result.ResultType;

public enum CustomResultTypeEnum implements ResultType {
    ERROR(GlobalResultTypeEnum.ERROR.getCode(), GlobalResultTypeEnum.ERROR.getMsg());

    private final String code;
    private final String msg;

    CustomResultTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
