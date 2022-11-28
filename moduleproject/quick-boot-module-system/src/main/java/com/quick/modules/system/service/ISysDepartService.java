package com.quick.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quick.modules.system.entity.SysDepart;
import com.quick.modules.system.model.SysDepartTreeModel;

import java.util.List;

/**
 * @author zhoujian
 * @projectName moduleproject
 * @description: TODO
 * @date 2022/9/4 23:22
 */
public interface ISysDepartService extends IService<SysDepart> {

    /**
     * 查询SysDepart集合
     * @param userId
     * @return
     */
    public List<SysDepart> queryUserDeparts(String userId);

    List<SysDepartTreeModel> queryTreeList();

    List<SysDepartTreeModel> queryTreeList(String ids);

}
