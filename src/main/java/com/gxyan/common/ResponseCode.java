package com.gxyan.common;

public enum ResponseCode {
    // 响应码及其描述
    SUCCESS(20000, "SUCCESS"), // 成功
    ERROR(1, "ERROR"),         // 通用错误
    NEED_LOGIN(10, "NEED_LOGIN"), // 需要登录
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"); // 非法参数

    private final int code; // 响应码
    private final String desc; // 描述

    // 构造函数
    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 获取响应码
    public int getCode() {
        return code;
    }

    // 获取描述
    public String getDesc() {
        return desc;
    }
}
