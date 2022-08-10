package com.quick.common.exception;

/**
 * 自定义异常
 *
 * @author zhoujian on 2022/7/8
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}