// package com.quick.modules.system.controller;
//
// import com.alibaba.fastjson.JSONObject;
// import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
// import com.baomidou.mybatisplus.core.metadata.IPage;
// import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
// import com.quick.common.api.vo.Result;
// import com.quick.common.constant.CommonConstant;
// import com.quick.common.constant.CommonSendStatus;
// import com.quick.common.constant.WebsocketConst;
// import com.quick.common.system.query.QueryGenerator;
// import com.quick.common.system.util.JwtUtil;
// import com.quick.modules.system.entity.SysAnnouncement;
// import com.quick.modules.system.service.ISysAnnouncementService;
// import com.quick.modules.system.utils.XSSUtils;
// import lombok.extern.slf4j.Slf4j;
// import okhttp3.WebSocket;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
//
// import javax.annotation.Resource;
// import javax.servlet.http.HttpServletRequest;
// import java.util.Date;
//
// /**
//  * 公告控制器
//  *
//  * @author zhoujian on 2022/9/8
//  */
// @RestController
// @RequestMapping("/sys/annountCement")
// @Slf4j
// public class SysAnnouncementController {
//
//     @Autowired
//     private ISysAnnouncementService sysAnnouncementService;
//     @Resource
//     private WebSocket webSocket;
//
//     /**
//      * 分页列表查询
//      * @param sysAnnouncement
//      * @param pageNo
//      * @param pageSize
//      * @param req
//      * @return
//      */
//     @RequestMapping(value = "/list", method = RequestMethod.GET)
//     public Result<IPage<SysAnnouncement>> queryPageList(SysAnnouncement sysAnnouncement,
//                                                         @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//                                                         @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//                                                         HttpServletRequest req) {
//         Result<IPage<SysAnnouncement>> result = new Result<IPage<SysAnnouncement>>();
//         sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
//         QueryWrapper<SysAnnouncement> sysAnnouncementQueryWrapper = QueryGenerator.initQueryWrapper(sysAnnouncement, req.getParameterMap());
//         Page<SysAnnouncement> page = new Page<>(pageNo, pageSize);
//         Page<SysAnnouncement> pageList = sysAnnouncementService.page(page,sysAnnouncementQueryWrapper);
//         result.setSuccess(true);
//         result.setResult(pageList);
//         return result;
//     }
//
//     /**
//      *   添加
//      * @param sysAnnouncement
//      * @return
//      */
//     @RequestMapping(value = "/add", method = RequestMethod.POST)
//     public Result<SysAnnouncement> add(@RequestBody SysAnnouncement sysAnnouncement) {
//         Result<SysAnnouncement> result = new Result<SysAnnouncement>();
//         try {
//             // 标题处理xss攻击的问题
//             String title = XSSUtils.striptXSS(sysAnnouncement.getTitile());
//             sysAnnouncement.setTitile(title);
//
//             sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_0.toString());
//             //未发布
//             sysAnnouncement.setSendStatus(CommonSendStatus.UNPUBLISHED_STATUS_0);
//             sysAnnouncementService.saveAnnouncement(sysAnnouncement);
//             result.success("添加成功！");
//         } catch (Exception e) {
//             log.error(e.getMessage(),e);
//             result.error500("操作失败");
//         }
//         return result;
//     }
//
//     /**
//      *  编辑
//      * @param sysAnnouncement
//      * @return
//      */
//     @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
//     public Result<SysAnnouncement> eidt(@RequestBody SysAnnouncement sysAnnouncement) {
//         Result<SysAnnouncement> result = new Result<SysAnnouncement>();
//         SysAnnouncement sysAnnouncementEntity = sysAnnouncementService.getById(sysAnnouncement.getId());
//         if(sysAnnouncementEntity==null) {
//             result.error500("未找到对应实体");
//         }else {
//             String title = XSSUtils.striptXSS(sysAnnouncement.getTitile());
//             sysAnnouncement.setTitile(title);
//             boolean ok = sysAnnouncementService.upDateAnnouncement(sysAnnouncement);
//             //TODO 返回false说明什么？
//             if(ok) {
//                 result.success("修改成功!");
//             }
//         }
//
//         return result;
//     }
//
//     /**
//      *   通过id删除
//      * @param id
//      * @return
//      */
//     @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//     public Result<SysAnnouncement> delete(@RequestParam(name="id",required=true) String id) {
//         Result<SysAnnouncement> result = new Result<SysAnnouncement>();
//         SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
//         if(sysAnnouncement==null) {
//             result.error500("未找到对应实体");
//         }else {
//             sysAnnouncement.setDelFlag(CommonConstant.DEL_FLAG_1.toString());
//             boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
//             if(ok) {
//                 result.success("删除成功!");
//             }
//         }
//
//         return result;
//     }
//
//     /**
//      *  批量删除
//      * @param ids
//      * @return
//      */
//     @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
//     public Result<SysAnnouncement> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//         Result<SysAnnouncement> result = new Result<SysAnnouncement>();
//         if(ids==null || "".equals(ids.trim())) {
//             result.error500("参数不识别！");
//         }else {
//             String[] id = ids.split(",");
//             for(int i=0;i<id.length;i++) {
//                 SysAnnouncement announcement = sysAnnouncementService.getById(id[i]);
//                 announcement.setDelFlag(CommonConstant.DEL_FLAG_1.toString());
//                 sysAnnouncementService.updateById(announcement);
//             }
//             result.success("删除成功!");
//         }
//         return result;
//     }
//
//     /**
//      * 通过id查询
//      * @param id
//      * @return
//      */
//     @RequestMapping(value = "/queryById", method = RequestMethod.GET)
//     public Result<SysAnnouncement> queryById(@RequestParam(name="id",required=true) String id) {
//         Result<SysAnnouncement> result = new Result<SysAnnouncement>();
//         SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
//         if(sysAnnouncement==null) {
//             result.error500("未找到对应实体");
//         }else {
//             result.setResult(sysAnnouncement);
//             result.setSuccess(true);
//         }
//         return result;
//     }
//
//     // /**
//     //  *	 更新发布操作
//     //  * @param id
//     //  * @return
//     //  */
//     // @RequestMapping(value = "/doReleaseData", method = RequestMethod.GET)
//     // public Result<SysAnnouncement> doReleaseData(@RequestParam(name="id",required=true) String id, HttpServletRequest request) {
//     //     Result<SysAnnouncement> result = new Result<SysAnnouncement>();
//     //     SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
//     //     if(sysAnnouncement==null) {
//     //         result.error500("未找到对应实体");
//     //     }else {
//     //         //发布中
//     //         sysAnnouncement.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);
//     //         sysAnnouncement.setSendTime(new Date());
//     //         String currentUserName = JwtUtil.getUserNameByToken(request);
//     //         sysAnnouncement.setSender(currentUserName);
//     //         boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
//     //         if(ok) {
//     //             result.success("该系统通知发布成功");
//     //             if(sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_ALL)) {
//     //                 JSONObject obj = new JSONObject();
//     //                 obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_TOPIC);
//     //                 obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
//     //                 obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
//     //                 webSocket.sendMessage(obj.toJSONString());
//     //             }else {
//     //                 // 2.插入用户通告阅读标记表记录
//     //                 String userId = sysAnnouncement.getUserIds();
//     //                 String[] userIds = userId.substring(0, (userId.length()-1)).split(",");
//     //                 String anntId = sysAnnouncement.getId();
//     //                 Date refDate = new Date();
//     //                 JSONObject obj = new JSONObject();
//     //                 obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
//     //                 obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
//     //                 obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
//     //                 webSocket.sendMessage(userIds, obj.toJSONString());
//     //             }
//     //             try {
//     //                 // 同步企业微信、钉钉的消息通知
//     //                 Response<String> dtResponse = dingtalkService.sendActionCardMessage(sysAnnouncement, true);
//     //                 wechatEnterpriseService.sendTextCardMessage(sysAnnouncement, true);
//     //
//     //                 if (dtResponse != null && dtResponse.isSuccess()) {
//     //                     String taskId = dtResponse.getResult();
//     //                     sysAnnouncement.setDtTaskId(taskId);
//     //                     sysAnnouncementService.updateById(sysAnnouncement);
//     //                 }
//     //             } catch (Exception e) {
//     //                 log.error("同步发送第三方APP消息失败：", e);
//     //             }
//     //         }
//     //     }
//     //
//     //     return result;
//     // }
// }