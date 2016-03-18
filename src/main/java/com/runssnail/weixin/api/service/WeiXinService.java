package com.runssnail.weixin.api.service;

import com.runssnail.weixin.api.request.Request;
import com.runssnail.weixin.api.response.Response;

/**
 * 微信服务
 *
 * Created by zhengwei on 2016/3/17.
 */
public interface WeiXinService {

    String getAppId();

    String getAppSecret();

    /**
     * 刷新access token
     */
    void refreshAccessToken();

    /**
     * 发送微信请求
     *
     * @param request
     * @param <R>
     * @return
     */
    <R extends Response> R execute(Request<R> request);
}