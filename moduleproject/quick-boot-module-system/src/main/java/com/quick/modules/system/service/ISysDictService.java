package com.quick.modules.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.quick.common.system.vo.DictModel;
import com.quick.modules.system.entity.SysDict;

import java.util.List;
import java.util.Map;

public interface ISysDictService extends IService<SysDict> {

    /**
     * 登录加载系统字典
     * @return
     */
    public Map<String, List<DictModel>> queryAllDictItems();

}
