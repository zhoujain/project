package com.quick.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quick.modules.system.entity.SysUser;
import com.quick.modules.system.entity.SysUserDepart;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface SysUserDepartMapper extends BaseMapper<SysUserDepart> {

	List<SysUserDepart> getUserDepartByUid(@Param("userId") String userId);

	/**
	 *  查询指定部门下的用户 并且支持用户真实姓名模糊查询
	 * @param orgCode
	 * @param realname
	 * @return
	 */
	List<SysUser> queryDepartUserList(@Param("orgCode") String orgCode, @Param("realname") String realname);

	/**
	 * 根据部门查询部门用户
	 * @param page
	 * @param orgCode
	 * @param username
	 * @param realname
	 * @return
	 */
	IPage<SysUser> queryDepartUserPageList(Page<SysUser> page, @Param("orgCode") String orgCode, @Param("username") String username, @Param("realname") String realname);
}
