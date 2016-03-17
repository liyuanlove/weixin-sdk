package com.runssnail.weixin.api.common;

import com.runssnail.weixin.api.domain.payment.JsApiPayReq;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.runssnail.weixin.api.constants.Constants.DEFAULT_ENCODING;

/**
 * 微信支付工具类
 *
 * Created by zhengwei on 2016/3/17.
 */
public class PaymentUtils {

    private static final Log log = LogFactory.getLog(PaymentUtils.class);

    /**
     * 生成js api 支付请求参数
     *
     * @param appId
     * @param prepayId 微信预支付单id
     * @param paySignKey 微信支付密钥
     * @return 支付签名，随机字符串，时间戳
     */
    public static JsApiPayReq buildJsApiPayReq(String appId, String prepayId, String paySignKey) {
        JsApiPayReq req = new JsApiPayReq();
        String nonceStr = SignUtils.buildNonce(DEFAULT_ENCODING);
        long timeStamp = System.currentTimeMillis() / 1000;
        String paySign = SignUtils.buildPaySign(appId, prepayId, paySignKey, nonceStr, timeStamp);

        req.setAppId(appId);
        req.setNonceStr(nonceStr);
        req.setTimeStamp(timeStamp);
        req.setPaySign(paySign);
        req.setPrepayId(prepayId);

        return req;
    }
}