package com.quick.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.quick.common.api.vo.Result;
import com.quick.common.util.Md5Util;
import com.quick.common.util.PasswordUtil;
import com.quick.common.util.RedisUtil;
import com.quick.modules.system.entity.SysDepart;
import com.quick.modules.system.entity.SysUser;
import com.quick.modules.system.model.SysLoginModel;
import com.quick.modules.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Autowired
    private ISysUserService sysUserService;

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
        SysUser sysUser = sysUserService.getOne(lambdaQueryWrapper);
        result = sysUserService.checkUserIsEffective(sysUser);
        if(!result.isSuccess()) {
            return result;
        }

        //2. 校验用户名和密码是否正确
        String userPassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
        String sysPassword = sysUser.getPassword();
        if (!sysPassword.equals(userPassword)){
            result.error500("用户名或密码错误");
            return result;
        }

        // 用户信息
        userInfo(sysUser, result);
        return result;
    }

    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private Result<JSONObject> userInfo(SysUser sysUser, Result<JSONObject> result) {
        String syspassword = sysUser.getPassword();
        String username = sysUser.getUsername();
        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
        obj.put("departs", departs);
    }
}
