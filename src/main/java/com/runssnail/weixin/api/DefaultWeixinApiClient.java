package com.runssnail.weixin.api;

import com.alibaba.fastjson.JSON;
import com.runssnail.weixin.api.common.RequestMethod;
import com.runssnail.weixin.api.constants.Constants;
import com.runssnail.weixin.api.exception.WeixinApiException;
import com.runssnail.weixin.api.internal.support.WeixinApiRuleValidate;
import com.runssnail.weixin.api.internal.utils.HttpUtils;
import com.runssnail.weixin.api.request.Request;
import com.runssnail.weixin.api.response.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * 默认的WeiXinApiClient服务实现
 *
 * @see WeixinApiClient
 *
 * @author zhengwei
 */
public class DefaultWeixinApiClient implements WeixinApiClient {

    private final Logger log = Logger.getLogger(getClass());

    private final static String ACCESS_TOKEN = "access_token";

    /**
     * 连接超时时间，单位毫秒，默认3秒
     */
    private int connectTimeout = Constants.DEFAULT_CONNECT_TIMEOUT;

    /**
     * 读取超时时间，单位毫秒，默认10秒
     */
    private int readTimeout = Constants.DEFAULT_READ_TIMEOUT;

    /**
     * 微信app id
     */
    private String appId;

    /**
     * 微信appSecret
     */
    private String appSecret;

    /**
     * 创建DefaultWeiXinApiClient
     *
     * @param appId     appId
     * @param appSecret appSecret
     */
    public DefaultWeixinApiClient(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @Override
    public <R extends Response> R execute(Request<R> req) throws WeixinApiException {
        return this.execute(req, null);
    }

    @Override
    public <R extends Response> R execute(Request<R> req, String accessToken) throws WeixinApiException {

        WeixinApiRuleValidate.notNull(req, "request is required");

        String apiUrl = buildApiUrl(req, accessToken);

        return executeInternal(apiUrl, req);
    }

    /**
     * 执行请求
     *
     * @param apiUrl 实际的apiurl
     * @param req    请求
     * @param <R>    响应对象
     * @return 响应对象
     */
    private <R extends Response> R executeInternal(String apiUrl, Request<R> req) {

        if (log.isDebugEnabled()) {
            log.debug("execute start, apiUrl=" + apiUrl + ", request=" + req);
        }

        long start = System.currentTimeMillis();

        req.check();

        RequestMethod method = req.getMethod();

        String result = StringUtils.EMPTY;

        try {
            if (method.isGet()) {
                result = HttpUtils.doGet(apiUrl, req.getParams());
            } else {
                result = HttpUtils.doPost(apiUrl, buildPostParams(req.getParams()), this.connectTimeout, this.readTimeout);
            }

            if (log.isDebugEnabled()) {
                log.debug("execute request success, apiUrl=" + apiUrl + ", request=" + req + ", result=" + result);
            }

        } catch (IOException e) {
            throw new WeixinApiException("execute request error, apiUrl=" + apiUrl + ", request=" + req + ", result=" + result, e);
        }

        R res = buildResponse(result, req);

        res.setResponseBody(result);

//        res.check();
        checkResponse(res);

        if (log.isDebugEnabled()) {
            log.debug("execute end, used total " + (System.currentTimeMillis() - start) + " ms, response=" + res);
        }
        return res;
    }

    /**
     * 检查响应数据是否正确，微信支付需要验证签名
     *
     * @param res
     * @param <R>
     */
    protected <R extends Response> void checkResponse(R res) {

    }

    /**
     * 将响应数据生成对象
     *
     * @param result 响应字符串
     * @param req 请求对象
     * @param <R> 响应对象
     * @return 响应对象
     */
    protected <R extends Response> R buildResponse(String result, Request<R> req) {
        return JSON.parseObject(result, req.getResponseClass());
    }

    /**
     * 生成post数据，默认生成json数据
     *
     * @param params
     * @return
     */
    protected String buildPostParams(Map<String, Object> params) {
        return JSON.toJSONString(params);
    }

    /**
     * 生成实际的api url，access token 不为空的话，自动加上
     *
     * @param req         api请求
     * @param accessToken accessToken
     * @return api url
     */
    private <R extends Response> String buildApiUrl(Request<R> req, String accessToken) {
        String apiUrl = req.getApiUrl();

        if (StringUtils.isBlank(accessToken)) {
            return apiUrl;
        }

        StringBuilder apiUrlBuilder = new StringBuilder(apiUrl);
        if (!apiUrl.contains("?")) {
            apiUrlBuilder.append("?");
        } else if (!apiUrl.endsWith("&")) {
            apiUrlBuilder.append("&");
        }
        apiUrlBuilder.append(ACCESS_TOKEN).append("=").append(accessToken);
        apiUrl = apiUrlBuilder.toString();
        return apiUrl;
    }

    @Override
    public String getAppId() {
        return this.appId;
    }

    @Override
    public String getAppSecret() {
        return this.appSecret;
    }

    @Override
    public void init() {
        // ignore
    }

    @Override
    public void close() {
        // ignore
    }

    /**
     * http 连接超时时间，单位毫秒
     *
     * @return http 连接超时时间，单位毫秒
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * 设置 http 连接超时时间，单位毫秒
     *
     * @param connectTimeout http 连接超时时间，单位毫秒
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * http 读取时间，单位毫秒
     *
     * @return http 读取时间，单位毫秒
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * 设置http 读取时间，单位毫秒
     *
     * @param readTimeout http 读取时间，单位毫秒
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
