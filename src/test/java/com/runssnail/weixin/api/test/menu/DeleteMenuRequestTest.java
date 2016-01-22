package com.runssnail.weixin.api.test.menu;

import com.runssnail.weixin.api.RetryWeixinApiClient;
import com.runssnail.weixin.api.request.menu.DeleteMenuRequest;
import com.runssnail.weixin.api.response.Response;
import com.runssnail.weixin.api.support.WeixinApiClients;

public class DeleteMenuRequestTest {

    public static void main(String[] args) {

        String appId = "wxe58afcd99f7a997e";
        String appSecret = "5dcf8eac1e99e983fc58e42376ab0267";

        RetryWeixinApiClient weixinApiClient = WeixinApiClients.buildRetryWeixinApiClient(appId, appSecret);

        DeleteMenuRequest req = new DeleteMenuRequest();

        Response res = weixinApiClient.execute(req, true);

        System.out.println(res);

        weixinApiClient.close();
    }
}
