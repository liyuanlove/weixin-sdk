package com.runssnail.weixin.api.service;

import org.apache.commons.lang.StringUtils;

/**
 * 用redis来存储ticket
 *
 * Created by zhengwei on 2016/3/31.
 */
public class RedisTicketService extends AbstractTicketService {

    /**
     * 保存在redis里key的前缀, prefix + appid
     */
    private static final String TICKET_KEY_PREFIX = "weixin:ticket:";

    private RedisClient redisClient;

    @Override
    protected void saveTicket(String ticket) {

        // 默认保存7200秒，也就是2个小时
        this.redisClient.set(TICKET_KEY_PREFIX + getAppId(), ticket, 7200);
    }

    private String getAppId() {
        return this.getWeiXinClient().getAppId();
    }

    @Override
    public String getTicket() {
        String ticket = redisClient.getString(TICKET_KEY_PREFIX + getAppId());

        if (StringUtils.isBlank(ticket)) {
            ticket = refresh();
        }
        return ticket;
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }
}