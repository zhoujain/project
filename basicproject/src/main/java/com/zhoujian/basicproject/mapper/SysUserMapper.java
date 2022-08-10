package com.zhoujian.basicproject.mapper;


import com.zhoujian.basicproject.domain.SysUser;

/**
 * 用户表 数据层
 *
 * @author zhoujian
 */
public interface SysUserMapper
{


    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);


}
