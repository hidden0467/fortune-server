package com.fortune.commonservice.enums;

import lombok.Getter;

@Getter
public enum BizCodeEnum {

    SUCCESS(0, "请求成功"),
    ERROR(-1, "请求失败");


    /**
     * 错误信息
     */
    private final String message;

    /**
     * 状态码
     */
    private final int code;

    BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

