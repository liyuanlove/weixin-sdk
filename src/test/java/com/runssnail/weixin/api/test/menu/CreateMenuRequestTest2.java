package com.runssnail.weixin.api.test.menu;

import com.runssnail.weixin.api.RetryWeiXinClient;
import com.runssnail.weixin.api.domain.menu.Menu;
import com.runssnail.weixin.api.request.menu.CreateMenuRequest;
import com.runssnail.weixin.api.response.Response;
import com.runssnail.weixin.api.manager.token.MemoryAccessTokenManager;
import com.runssnail.weixin.api.support.WeiXinClients;

import java.util.ArrayList;
import java.util.List;

public class CreateMenuRequestTest2 {

    public static void main(String[] args) {
        String appId = "";
        String appSecret = "";

        RetryWeiXinClient weixinApiClient = null;
        try {
            weixinApiClient = WeiXinClients.buildRetryWeiXinClient(appId, appSecret);

            MemoryAccessTokenManager accessTokenService = new MemoryAccessTokenManager();
            accessTokenService.setWeixinClient(weixinApiClient);

            List<Menu> menus = new ArrayList<Menu>();

//            Menu click = new Menu("view", "歌曲");
//            click.setKey("fajflajlf");
//            menus.add(click);
//
//            List<Menu> subMenus = new ArrayList<Menu>();
//            Menu viewMenu1 = new Menu("view", "搜索");
//            viewMenu1.setUrl("http://www.soso.com/");
//            subMenus.add(viewMenu1);
//
//            Menu click1 = new Menu("click", "2级");
//            click1.setKey("fajflajlaaaaf");
//            subMenus.add(click1);
//
//            Menu notFunc = new Menu("一级菜单", subMenus);

            Menu viewMenu = new Menu("view", "游戏");
            viewMenu.setUrl("http://m.juboyi.com:81/game/index.htm");
            menus.add(viewMenu);

            Menu viewMenu1 = new Menu("view", "挖铜板");
            viewMenu1.setUrl("http://m.juboyi.com:81/");
//            viewMenu1.setUrl("http://bm.watongban.cn/");
            menus.add(viewMenu1);

            Menu viewMenu2 = new Menu("view", "我的");
            viewMenu2.setUrl("http://m.juboyi.com:81/member/index.htm");
//            viewMenu2.setUrl("http://bm.watongban.cn/member/index.htm");
            menus.add(viewMenu2);

            CreateMenuRequest req = new CreateMenuRequest(menus);

            Response res = weixinApiClient.execute(req, accessTokenService.getAccessToken());

            System.out.println(res);

        } finally {
            if (weixinApiClient != null) {
                weixinApiClient.close();
            }
        }

    }

}
