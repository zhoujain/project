package com.quick.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.quick.common.api.vo.Result;
import com.quick.common.util.Md5Util;
import com.quick.common.util.RedisUtil;
import com.quick.modules.system.entity.SysUser;
import com.quick.modules.system.model.SysLoginModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author zhoujian on 2022/8/31
 */
@RestController
@Slf4j
@RequestMapping("/sys")
@Api("用户登录")
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel){
        Result<JSONObject> result = new Result<JSONObject>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();
        String captcha = sysLoginModel.getCaptcha();
        if (captcha == null){
            return result.error500("验证码无效");
        }
        String lowerCaseCaptcha = captcha.toLowerCase();
        // 获取MD5加密后数据
        String realKey = Md5Util.md5Encode(sysLoginModel.getCheckKey(), lowerCaseCaptcha);
        Object checkCode = redisUtil.get(realKey);
        if (checkCode == null || !checkCode.toString().equals(lowerCaseCaptcha)){
            log.warn("验证码错误，key= {} , Ui checkCode= {}, Redis checkCode = {}", sysLoginModel.getCheckKey(), lowerCaseCaptcha, checkCode);
            result.error500("验证码错误");
            return result;
        }

        // 1.验证用户是否有效
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUsername, username);
        return result;
    }
}