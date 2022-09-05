package com.quick.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quick.modules.system.entity.SysAnnouncementSend;
import com.quick.modules.system.model.AnnouncementSendModel;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @Description: 用户通告阅读标记表
 * @Author: jeecg-boot
 * @Date:  2019-02-21
 * @Version: V1.0
 */
public interface SysAnnouncementSendMapper extends BaseMapper<SysAnnouncementSend> {

    /**
     * 通过用户id查询 用户通告阅读标记表
     * @param userId 用户id
     * @return
     */
	public List<String> queryByUserId(@Param("userId") String userId);

	/**
	 * 获取我的消息
	 * @param announcementSendModel
	 * @param page
	 * @return
	 */
	public List<AnnouncementSendModel> getMyAnnouncementSendList(Page<AnnouncementSendModel> page, @Param("announcementSendModel") AnnouncementSendModel announcementSendModel);

}
