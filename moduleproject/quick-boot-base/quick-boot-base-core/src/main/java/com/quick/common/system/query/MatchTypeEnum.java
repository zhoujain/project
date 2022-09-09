package com.quick.common.system.query;


import com.quick.common.util.MyConvertUtils;

/**
 * 查询链接规则
 *
 * @Author zhoujian
 */
public enum MatchTypeEnum {

    /**查询链接规则 AND*/
    AND("AND"),
    /**查询链接规则 OR*/
    OR("OR");

    private String value;

    MatchTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MatchTypeEnum getByValue(Object value) {
        if (MyConvertUtils.isEmpty(value)) {
            return null;
        }
        return getByValue(value.toString());
    }

    public static MatchTypeEnum getByValue(String value) {
        if (MyConvertUtils.isEmpty(value)) {
            return null;
        }
        for (MatchTypeEnum val : values()) {
            if (val.getValue().toLowerCase().equals(value.toLowerCase())) {
                return val;
            }
        }
        return null;
    }
}
