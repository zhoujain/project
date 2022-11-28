package com.quick.modules.system.controller;

import com.quick.common.api.vo.Result;
import com.quick.common.system.vo.SysDepartModel;
import com.quick.common.util.MyConvertUtils;
import com.quick.modules.system.model.SysDepartTreeModel;
import com.quick.modules.system.service.ISysDepartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * <p>
 *
 */
@RestController
@RequestMapping("/sys/sysDepart")
@Slf4j
public class SysDepartController {
    @Autowired
    private ISysDepartService sysDepartService;

    @GetMapping("/queryTreeList")
    public Result<List<SysDepartTreeModel>> queryTreeList(@RequestParam(name = "ids", required = false) String ids){
        Result<List<SysDepartTreeModel>> result = new Result<>();
        try {

            if(MyConvertUtils.isNotEmpty(ids)){
                List<SysDepartTreeModel> departList = sysDepartService.queryTreeList(ids);
                result.setResult(departList);
            }else{
                List<SysDepartTreeModel> list = sysDepartService.queryTreeList();
                result.setResult(list);
            }
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return result;
    }
}
