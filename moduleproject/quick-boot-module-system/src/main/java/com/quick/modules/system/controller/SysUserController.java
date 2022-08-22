package com.quick.modules.system.controller;


import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.quick.common.api.vo.Result;
import com.quick.modules.system.entity.SysUser;
import com.quick.modules.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@Slf4j
@RestController
@RequestMapping("/sys/user")
@Api(tags = "用户操作API")
@ApiSort(2)
public class SysUserController {

	@Autowired
	private ISysUserService sysUserService;


    @GetMapping(value = "/queryById")
    @ApiOperation(value = "查询用户",notes = "测试")
    public Result<SysUser> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<SysUser> result = new Result<SysUser>();
        SysUser sysUser = sysUserService.getById(id);
        if (sysUser == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(sysUser);
            result.setSuccess(true);
        }
        return result;
    }

//    @RequestMapping(value = "/queryUserRole", method = RequestMethod.GET)
//    public Result<List<String>> queryUserRole(@RequestParam(name = "userid", required = true) String userid) {
//        Result<List<String>> result = new Result<>();
//        List<String> list = new ArrayList<String>();
//        List<SysUserRole> userRole = sysUserRoleService.list(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, userid));
//        if (userRole == null || userRole.size() <= 0) {
//            result.error500("未找到用户相关角色信息");
//        } else {
//            for (SysUserRole sysUserRole : userRole) {
//                list.add(sysUserRole.getRoleId());
//            }
//            result.setSuccess(true);
//            result.setResult(list);
//        }
//        return result;
//    }
}
