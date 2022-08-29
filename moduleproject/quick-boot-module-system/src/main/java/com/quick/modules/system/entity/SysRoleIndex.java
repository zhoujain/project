package com.quick.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 角色首页配置
 * @Author: liusq
 * @Date:   2022-03-25
 * @Version: V1.0
 */
@Data
@TableName("sys_role_index")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_role_index对象", description="角色首页配置")
public class SysRoleIndex {
    
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private String id;
	/**角色编码*/
	@Excel(name = "角色编码", width = 15)
    @ApiModelProperty(value = "角色编码")
	private String roleCode;
	/**路由地址*/
	@Excel(name = "路由地址", width = 15)
    @ApiModelProperty(value = "路由地址")
	private String url;
	/**优先级*/
	@Excel(name = "优先级", width = 15)
    @ApiModelProperty(value = "优先级")
	private Integer priority;
	/**路由地址*/
	@Excel(name = "状态", width = 15)
	@ApiModelProperty(value = "状态")
	private String status;
	/**创建人登录名称*/
	@Excel(name = "创建人登录名称", width = 15)
    @ApiModelProperty(value = "创建人登录名称")
	private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private Date createTime;
	/**更新人登录名称*/
	@Excel(name = "更新人登录名称", width = 15)
    @ApiModelProperty(value = "更新人登录名称")
	private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
	private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
	private String sysOrgCode;
}
