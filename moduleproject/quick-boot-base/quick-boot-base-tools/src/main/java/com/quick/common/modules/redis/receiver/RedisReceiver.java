package com.quick.common.modules.redis.receiver;


import cn.hutool.core.util.ObjectUtil;
import com.quick.common.base.BaseMap;
import com.quick.common.constant.GlobalConstants;
import com.quick.common.modules.redis.listener.BusinessRedisListener;
import com.quick.common.util.SpringContextHolder;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author zyf
 */
@Component
@Data
public class RedisReceiver {


    /**
     * 接受消息并调用业务逻辑处理器
     *
     * @param params
     */
    public void onMessage(BaseMap params) {
        // 业务处理器beanName传递参数
        Object handlerName = params.get(GlobalConstants.HANDLER_NAME);
        BusinessRedisListener messageListener = SpringContextHolder.getHandler(handlerName.toString(), BusinessRedisListener.class);
        if (ObjectUtil.isNotEmpty(messageListener)) {
            messageListener.onMessage(params);
        }
    }

}
