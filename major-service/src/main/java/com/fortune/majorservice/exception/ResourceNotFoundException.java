package com.fortune.majorservice.exception;

import com.fortune.commonservice.enums.BizCodeEnum;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(BizCodeEnum.NOT_FOUND, message);
    }
}
