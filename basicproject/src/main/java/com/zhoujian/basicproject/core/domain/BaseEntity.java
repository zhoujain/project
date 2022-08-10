package com.zhoujian.basicproject.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Entity基类
 *
 * @author zhoujian
 */
@Data
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** 搜索值 */
    //数据库没有的字段
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    //没有的字段
    private String remark;

    /** 请求参数 */
    private Map<String, Object> params;

}
