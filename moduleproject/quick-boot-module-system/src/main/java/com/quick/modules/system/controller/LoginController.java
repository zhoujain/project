package com.quick.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.quick.common.api.vo.Result;
import com.quick.common.constant.CommonConstant;
import com.quick.common.system.util.JwtUtil;
import com.quick.common.util.Md5Util;
import com.quick.common.util.MyConvertUtils;
import com.quick.common.util.PasswordUtil;
import com.quick.common.util.RedisUtil;
import com.quick.modules.system.entity.SysDepart;
import com.quick.modules.system.entity.SysTenant;
import com.quick.modules.system.entity.SysUser;
import com.quick.modules.system.model.SysLoginModel;
import com.quick.modules.system.service.ISysDepartService;
import com.quick.modules.system.service.ISysTenantService;
import com.quick.modules.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    @Autowired
    private ISysDepartService sysDepartService;
    @Autowired
    private ISysTenantService sysTenantService;

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
        if (departs == null || departs.size() == 0){
            obj.put("multi_depart",0);
        }else if (departs.size() == 1) {
            sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
            obj.put("multi_depart", 1);
        } else {
            //查询当前是否有登录部门
            // update-begin--Author:wangshuai Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
            SysUser sysUserById = sysUserService.getById(sysUser.getId());
            if(MyConvertUtils.isEmpty(sysUserById.getOrgCode())){
                sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
            }
            // update-end--Author:wangshuai Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
            obj.put("multi_depart", 2);
        }
        // update-begin--Author:sunjianlei Date:20210802 for：获取用户租户信息
        String tenantIds = sysUser.getRelTenantIds();
        if (MyConvertUtils.isNotEmpty(tenantIds)) {
            List<Integer> tenantIdList = new ArrayList<>();
            for(String id: tenantIds.split(",")){
                tenantIdList.add(Integer.valueOf(id));
            }
            // 该方法仅查询有效的租户，如果返回0个就说明所有的租户均无效。
            List<SysTenant> tenantList = sysTenantService.queryEffectiveTenant(tenantIdList);
            if (tenantList.size() == 0) {
                result.error500("与该用户关联的租户均已被冻结，无法登录！");
                return result;
            } else {
                obj.put("tenantList", tenantList);
            }
        }
        // update-end--Author:sunjianlei Date:20210802 for：获取用户租户信息
        // 生成token
        String token = JwtUtil.sign(username, syspassword);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        obj.put("token", token);
        obj.put("userInfo", sysUser);
        obj.put("sysAllDictItems", sysDictService.queryAllDictItems());
        result.setResult(obj);
        result.success("登录成功");
    }
}
