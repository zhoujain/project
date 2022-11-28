package com.quick.modules.system.service.ipml;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quick.common.constant.CommonConstant;
import com.quick.modules.system.entity.SysDepart;
import com.quick.modules.system.mapper.SysDepartMapper;
import com.quick.modules.system.model.SysDepartTreeModel;
import com.quick.modules.system.service.ISysDepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * @author zhoujian
 * @projectName moduleproject
 * @description: 用户部门业务层
 * @date 2022/9/4 23:23
 */
@Service
public class SysDepartServiceImpl extends ServiceImpl<SysDepartMapper, SysDepart> implements ISysDepartService {
    @Autowired
    private SysDepartMapper sysDepartMapper;

    @Override
    public List<SysDepart> queryUserDeparts(String userId) {
        return sysDepartMapper.queryUserDeparts(userId);
    }

    /**
     * 查询所有部门信息,并分节点进行显示
     * 查询所有的部门数据,以树结构形式响应给前端
     * @return
     */
    @Override
    public List<SysDepartTreeModel> queryTreeList() {
        LambdaQueryWrapper<SysDepart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDepart::getDelFlag, CommonConstant.DEL_FLAG_0.toString());
        lambdaQueryWrapper.orderByAsc(SysDepart::getDepartOrder);
        List<SysDepart> list = this.list(lambdaQueryWrapper);

        return null;
    }

    @Override
    public List<SysDepartTreeModel> queryTreeList(String ids) {
        return null;
    }

}
