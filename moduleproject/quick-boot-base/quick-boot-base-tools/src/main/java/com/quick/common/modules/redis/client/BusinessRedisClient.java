package com.quick.common.modules.redis.client;

import com.quick.common.base.BaseMap;
import com.quick.common.constant.GlobalConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * redis客户端
 *
 * @author zhoujian on 2022/9/16
 */
@Configuration
public class BusinessRedisClient {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 发送消息
     */
    public void sendMessage(String handlerName, BaseMap params){
        params.put(GlobalConstants.HANDLER_NAME, handlerName);
        redisTemplate.convertAndSend(GlobalConstants.REDIS_TOPIC_NAME, params);
    }
}