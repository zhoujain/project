package com.quick.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quick.common.api.vo.Result;
import com.quick.common.constant.CommonConstant;
import com.quick.common.system.query.QueryGenerator;
import com.quick.modules.system.entity.SysAnnouncement;
import com.quick.modules.system.service.ISysAnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 公告控制器
 *
 * @author zhoujian on 2022/9/8
 */
@RestController
@RequestMapping("/sys/annountCement")
@Slf4j
public class SysAnnouncementController {

    @Autowired
    private ISysAnnouncementService sysAnnouncementService;

    /**
     * 分页列表查询
     * @param sysAnnouncement
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<SysAnnouncement>> queryPageList(SysAnnouncement sysAnnouncement,
                                                        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                        HttpServletRequest req) {
        Result<IPage<SysAnnouncement>> result = new Result<IPage<SysAnnouncement>>();
        sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
        QueryWrapper<SysAnnouncement> sysAnnouncementQueryWrapper = QueryGenerator.initQueryWrapper(sysAnnouncement, req.getParameterMap());
        Page<SysAnnouncement> page = new Page<>(pageNo, pageSize);
        Page<SysAnnouncement> pageList = sysAnnouncementService.page(page,sysAnnouncementQueryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
}