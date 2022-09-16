package com.quick.common.system.base.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quick.common.system.base.entity.BusinessEntity;
import com.quick.common.system.base.service.BusinessService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: ServiceImpl基类
 * @Author: dangzhenghui@163.com
 * @Date: 2019-4-21 8:13
 * @Version: 1.0
 */
@Slf4j
public class BusinessServiceImpl<M extends BaseMapper<T>, T extends BusinessEntity> extends ServiceImpl<M, T> implements BusinessService<T> {

}
