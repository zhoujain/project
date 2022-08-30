package com.quick.common.exception;

import com.quick.common.api.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 全局异常处理
 *
 * @author zhoujian on 2022/8/30
 */
@RestControllerAdvice
@Slf4j
public class BusinessExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<T> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        log.error(e.getMessage(),e);
        return Result.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }
}