package com.runssnail.weixin.api.manager;

import java.io.Serializable;

/**
 *  redis client
 *
 * Created by zhengwei on 16/4/11.
 */
public interface RedisClient {

    /**
     * 获取key对应的值
     *
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 设置key-value
     *
     * @param key 键
     * @param value 值
     * @param liveTime 存活时间,单位秒
     */
    void set(String key, Serializable value, long liveTime);
}
