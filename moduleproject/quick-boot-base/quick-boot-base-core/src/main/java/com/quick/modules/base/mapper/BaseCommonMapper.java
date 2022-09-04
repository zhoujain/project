package com.quick.modules.base.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.quick.common.api.dto.LogDTO;
import org.apache.ibatis.annotations.Param;

public interface BaseCommonMapper {

    /**
     * 保存日志
     * @param dto
     */
    //@SqlParser(filter=true)
    @InterceptorIgnore(illegalSql = "true", tenantLine = "true")
    void saveLog(@Param("dto") LogDTO dto);

}
