package com.fortune.majorservice.advice;

import com.fortune.commonservice.enums.BizCodeEnum;
import com.fortune.commonservice.feign.common.BeeResponseEntity;
import com.fortune.majorservice.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public BeeResponseEntity<Void> handleBusinessException(BusinessException exception) {
        return BeeResponseEntity.buildError(exception.getBizCodeEnum(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BeeResponseEntity<Void> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .orElse(BizCodeEnum.BAD_REQUEST.getMessage());
        return BeeResponseEntity.buildError(BizCodeEnum.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public BeeResponseEntity<Void> handleException(Exception exception, HttpServletRequest request) {
        log.error("Unhandled exception on {}", request.getRequestURI(), exception);
        return BeeResponseEntity.buildError(BizCodeEnum.INTERNAL_ERROR);
    }
}
