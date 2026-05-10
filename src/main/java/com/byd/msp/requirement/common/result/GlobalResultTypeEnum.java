package com.byd.msp.requirement.common.result;

public enum GlobalResultTypeEnum implements ResultType {
    SUCCESS("200", "操作成功"),
    ERROR("500", "系统错误");

    private final String code;
    private final String msg;

    GlobalResultTypeEnum(String code, String msg) {
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
