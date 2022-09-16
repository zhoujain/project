package com.quick.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.quick.common.system.base.entity.BusinessEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 消息模板
 * @Author: jeecg-boot
 * @Date:  2019-04-09
 * @Version: V1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_sms_template")
public class SysMessageTemplate extends BusinessEntity {
	/**模板CODE*/
	@Excel(name = "模板CODE", width = 15)
	private String templateCode;
	/**模板标题*/
	@Excel(name = "模板标题", width = 30)
	private String templateName;
	/**模板内容*/
	@Excel(name = "模板内容", width = 50)
	private String templateContent;
	/**模板测试json*/
	@Excel(name = "模板测试json", width = 15)
	private String templateTestJson;
	/**模板类型*/
	@Excel(name = "模板类型", width = 15)
	private String templateType;
}
