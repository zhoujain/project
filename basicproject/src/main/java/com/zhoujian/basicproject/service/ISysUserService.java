package com.zhoujian.basicproject.service;

import com.zhoujian.basicproject.domain.SysUser;

/**
 * 用户 业务层
 *
 * @author zhoujian
 */
public interface ISysUserService
{


    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);


}
