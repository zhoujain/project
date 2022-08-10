package com.quick.modules.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.quick.common.api.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/sys/common")
@Api(tags = "通用控制器")
@ApiSort(1)
public class CommonController {


    @GetMapping("/403")
    @ApiOperation(value = "测试",notes = "测试")
    public Result<?> noAuth(){
        return Result.error("没有权限，请联系管理员授权");
    }

    @GetMapping("/index")
    @ApiOperation(value = "测试一",notes = "测试看看")
    public String sayHello() {
        return "index";
    }



}
