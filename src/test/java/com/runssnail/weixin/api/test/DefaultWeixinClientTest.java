package com.runssnail.weixin.api.test;

import com.runssnail.weixin.api.DefaultWeixinClient;
import com.runssnail.weixin.api.WeixinClient;
import com.runssnail.weixin.api.request.token.GetAccessTokenRequest;
import com.runssnail.weixin.api.response.token.GetAccessTokenResponse;

/**
 * Created by zhengwei on 2016/11/24.
 */
public class DefaultWeixinClientTest {

    public static void main(String[] args) {

        // zw test
        String appId = "wxe58afcd99f7a997e";
        String appSecret = "5dcf8eac1e99e983fc58e42376ab0267";

        // 创建DefaultWeixinClient
        WeixinClient weiXinClient = new DefaultWeixinClient(appId, appSecret);
        try {

            // 创建GetAccessTokenRequest
            GetAccessTokenRequest req = new GetAccessTokenRequest();

            // 获取access token
            GetAccessTokenResponse res = weiXinClient.execute(req);

            System.out.println(res.getAccessToken());
        } finally {
            if (weiXinClient != null) {
                weiXinClient.close();
            }
        }
    }
}
