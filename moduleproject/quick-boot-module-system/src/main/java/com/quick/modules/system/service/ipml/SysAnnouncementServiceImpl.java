package com.quick.modules.system.service.ipml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.quick.common.constant.CommonConstant;
import com.quick.common.util.MyConvertUtils;
import com.quick.modules.system.entity.SysAnnouncement;
import com.quick.modules.system.entity.SysAnnouncementSend;
import com.quick.modules.system.mapper.SysAnnouncementMapper;
import com.quick.modules.system.mapper.SysAnnouncementSendMapper;
import com.quick.modules.system.service.ISysAnnouncementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Description: 系统通告表
 * @Author: jeecg-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Service
public class SysAnnouncementServiceImpl extends ServiceImpl<SysAnnouncementMapper, SysAnnouncement> implements ISysAnnouncementService {

	@Resource
	private SysAnnouncementMapper sysAnnouncementMapper;
	
	@Resource
	private SysAnnouncementSendMapper sysAnnouncementSendMapper;


	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveAnnouncement(SysAnnouncement sysAnnouncement) {
		if (sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_ALL)){
			sysAnnouncementMapper.insert(sysAnnouncement);
		}else {
			// 1.插入通告记录表
			sysAnnouncementMapper.insert(sysAnnouncement);
			// 2.插入用户通道阅读记录表
			String userId = sysAnnouncement.getUserIds();
			String[] userIds = userId.substring(0, userId.length() - 1).split(",");
			String antId = sysAnnouncement.getId();
			Date refDate = new Date();
			for (int i = 0; i < userIds.length; i++) {
				SysAnnouncementSend sysAnnouncementSend = new SysAnnouncementSend();
				sysAnnouncementSend.setAnntId(antId);
				sysAnnouncementSend.setUserId(userIds[i]);
				sysAnnouncementSend.setReadTime(refDate);
				sysAnnouncementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
				sysAnnouncementSendMapper.insert(sysAnnouncementSend);
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean upDateAnnouncement(SysAnnouncement sysAnnouncement) {
		// 1.更新系统表数据
		sysAnnouncementMapper.updateById(sysAnnouncement);
		String userId = sysAnnouncement.getUserIds();
		if (MyConvertUtils.isNotEmpty(userId) && sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_UESR)){
			// 2.补充新的数据通知用户
			String[] userIds = userId.substring(0, (userId.length()-1)).split(",");
			String anntId = sysAnnouncement.getId();
			Date refDate = new Date();
			for(int i=0;i<userIds.length;i++) {
				LambdaQueryWrapper<SysAnnouncementSend> queryWrapper = new LambdaQueryWrapper<SysAnnouncementSend>();
				queryWrapper.eq(SysAnnouncementSend::getAnntId, anntId);
				queryWrapper.eq(SysAnnouncementSend::getUserId, userIds[i]);
				List<SysAnnouncementSend> announcementSends=sysAnnouncementSendMapper.selectList(queryWrapper);
				if(announcementSends.size()<=0) {
					SysAnnouncementSend announcementSend = new SysAnnouncementSend();
					announcementSend.setAnntId(anntId);
					announcementSend.setUserId(userIds[i]);
					announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
					announcementSend.setReadTime(refDate);
					sysAnnouncementSendMapper.insert(announcementSend);
				}
			}
			// 3. 删除多余通知用户数据
			Collection<String> delUserIds = Arrays.asList(userIds);
			LambdaQueryWrapper<SysAnnouncementSend> queryWrapper = new LambdaQueryWrapper<SysAnnouncementSend>();
			queryWrapper.notIn(SysAnnouncementSend::getUserId, delUserIds);
			queryWrapper.eq(SysAnnouncementSend::getAnntId, anntId);
			sysAnnouncementSendMapper.delete(queryWrapper);
		}
		return true;
	}
}
