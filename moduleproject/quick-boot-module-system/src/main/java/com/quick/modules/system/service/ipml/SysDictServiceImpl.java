package com.quick.modules.system.service.ipml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quick.common.system.vo.DictModel;
import com.quick.modules.system.entity.SysDict;
import com.quick.modules.system.entity.SysDictItem;
import com.quick.modules.system.mapper.SysDictItemMapper;
import com.quick.modules.system.mapper.SysDictMapper;
import com.quick.modules.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典表的操作
 *
 * @author zhoujian on 2022/9/7
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {


    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private SysDictItemMapper sysDictItemMapper;

    @Override
    public Map<String, List<DictModel>> queryAllDictItems() {
        Map<String, List<DictModel>> res = new HashMap<>(5);
        List<SysDict> ls = sysDictMapper.selectList(null);
        LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<SysDictItem>();
        queryWrapper.eq(SysDictItem::getStatus, 1);
        queryWrapper.orderByAsc(SysDictItem::getSortOrder);
        List<SysDictItem> sysDictItemList = sysDictItemMapper.selectList(queryWrapper);
        for (SysDict sysDict : ls) {
            List<DictModel> dictModels = sysDictItemList.stream().filter(sysDictItem -> sysDictItem.getDictId().equals(sysDict.getId())).map(sysDictItem -> {
                DictModel dictModel = new DictModel();
                dictModel.setValue(sysDictItem.getItemValue());
                dictModel.setText(sysDictItem.getItemText());
                return dictModel;
            }).collect(Collectors.toList());

            res.put(sysDict.getDictCode(),dictModels);
        }
        log.debug("-------登录加载系统字典-----" + res.toString());
        return res;
    }
}