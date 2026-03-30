package com.fortune.majorservice.exception;

import com.fortune.commonservice.enums.BizCodeEnum;

public class BusinessException extends RuntimeException {

    private final BizCodeEnum bizCodeEnum;

    public BusinessException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMessage());
        this.bizCodeEnum = bizCodeEnum;
    }

    public BusinessException(BizCodeEnum bizCodeEnum, String message) {
        super(message);
        this.bizCodeEnum = bizCodeEnum;
    }

    public BizCodeEnum getBizCodeEnum() {
        return bizCodeEnum;
    }
}
