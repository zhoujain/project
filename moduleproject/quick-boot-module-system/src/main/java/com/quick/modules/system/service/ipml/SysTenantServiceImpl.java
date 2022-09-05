package com.quick.modules.system.service.ipml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quick.common.constant.CommonConstant;
import com.quick.common.exception.BusinessException;
import com.quick.modules.system.entity.SysTenant;
import com.quick.modules.system.entity.SysUser;
import com.quick.modules.system.mapper.SysTenantMapper;
import com.quick.modules.system.service.ISysTenantService;
import com.quick.modules.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("sysTenantServiceImpl")
@Slf4j
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

    @Autowired
    ISysUserService userService;

    @Override
    public List<SysTenant> queryEffectiveTenant(Collection<Integer> idList) {
        LambdaQueryWrapper<SysTenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysTenant::getId, idList);
        queryWrapper.eq(SysTenant::getStatus, Integer.valueOf(CommonConstant.STATUS_1));
        //此处查询忽略时间条件
        return super.list(queryWrapper);
    }

    @Override
    public long countUserLinkTenant(String id) {
        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(SysUser::getRelTenantIds, id);
        userQueryWrapper.or().like(SysUser::getRelTenantIds, "%," + id);
        userQueryWrapper.or().like(SysUser::getRelTenantIds, id + ",%");
        userQueryWrapper.or().like(SysUser::getRelTenantIds, "%," + id + ",%");
        // 查找出已被关联的用户数量
        return userService.count(userQueryWrapper);
    }

    @Override
    public boolean removeTenantById(String id) {
        // 查找出已被关联的用户数量
        long userCount = this.countUserLinkTenant(id);
        if (userCount > 0) {
            throw new BusinessException(("该租户已被引用，无法删除！");
        }
        return super.removeById(Integer.parseInt(id));
    }

}
