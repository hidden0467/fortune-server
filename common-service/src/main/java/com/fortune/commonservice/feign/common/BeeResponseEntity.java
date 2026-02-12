package com.fortune.commonservice.feign.common;

import com.fortune.commonservice.enums.BizCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeeResponseEntity<T> {

    /**
     * 状态码 0 表示成功
     */

    private Integer code;
    /**
     * 数据
     */
    private T data;
    /**
     * 描述
     */
    private String msg;

    public boolean successed() {
        if (Objects.isNull(code))
            return false;
        return code.equals(BizCodeEnum.SUCCESS.getCode());
    }

    /**
     * 成功，不传入数据
     */
    public static <T> BeeResponseEntity<T> buildSuccess() {
        return new BeeResponseEntity<>(0, null, null);
    }

    /**
     * 成功，传入数据
     */
    public static <T> BeeResponseEntity<T> buildSuccess(T data) {
        return new BeeResponseEntity<>(0, data, null);
    }

    /**
     * 失败，传入描述信息
     */
    public static <T> BeeResponseEntity<T> buildError(String msg) {
        return new BeeResponseEntity<>(-1, null, msg);
    }

    /**
     * 失败，传入描述信息
     */
    public static <T> BeeResponseEntity<T> buildError(BizCodeEnum bizCodeEnum, String msg) {
        return new BeeResponseEntity<>(bizCodeEnum.getCode(), null, msg);
    }

    /**
     * 传入枚举，返回失败信息
     */
    public static <T> BeeResponseEntity<T> buildError(BizCodeEnum codeEnum) {
        return BeeResponseEntity.buildCodeAndMsg(codeEnum.getCode(), codeEnum.getMessage());
    }


    /**
     * 自定义状态码和错误信息
     */
    public static <T> BeeResponseEntity<T> buildCodeAndMsg(int code, String msg) {
        return new BeeResponseEntity<>(code, null, msg);
    }

    /**
     * 传入枚举，返回信息
     */
    public static <T> BeeResponseEntity<T> buildResult(BizCodeEnum codeEnum) {
        return BeeResponseEntity.buildCodeAndMsg(codeEnum.getCode(), codeEnum.getMessage());
    }

}

