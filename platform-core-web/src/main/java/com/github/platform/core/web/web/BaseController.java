package com.github.platform.core.web.web;

import com.github.platform.core.common.service.BaseBizCommon;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.exception.AdapterException;
import com.github.platform.core.standard.exception.BaseResult;
import com.github.platform.core.web.util.RequestHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * base controller
 * @author: yxkong
 * @date: 2022/4/25 7:24 PM
 * @version: 1.0
 */
public class BaseController extends BaseBizCommon {

    /**
     * 抛出AdapterException层业务自定义异常
     * @param status 状态码
     * @param msg 信息
     * @return
     */
    protected AdapterException exception(String status, String msg){
        throw new AdapterException(status,msg);
    }

    /**
     * 抛出 AdapterException层业务自定义异常
     * @param result 实现BaseResult 接口的枚举，可参考：{@link com.github.platform.core.sys.domain.constant.SysResultEnum}
     * @return
     */
    protected AdapterException exception(BaseResult result){
        throw new AdapterException(result);
    }

    /**
     * 抛出 AdapterException层业务自定义异常
     * @param resultStatusEnum 通用异常枚举
     * @return
     */
    protected AdapterException exception(ResultStatusEnum resultStatusEnum){
        throw new AdapterException(resultStatusEnum);
    }
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    String redirect(String url) {
        return String.format("redirect:{}", url);
    }

    /**
     * 获取请求的url
     *
     * @return
     * @author yxkong
     * @createDate 2017年9月29日下午3:55:53
     * @updateDate
     * @备注
     */
    public String getMethodUrl() {
        return RequestHolder.getRequest().getRequestURI();
    }

    /**
     * 获取header中的token
     *
     * @return
     * @author yxkong
     * @createDate 2016年12月1日
     * @updateDate
     */
    public String getToken() {
        return getHeader(HeaderConstant.TOKEN);
    }

    /**
     * 获取header中的tenant
     *
     * @return
     */
    public String getTenant() {
        return getHeader(HeaderConstant.TENANT_ID);
    }

    public String getHeader(String k) {
        return RequestHolder.getHeaderVal(k);
    }

    public HttpServletRequest getRequest() {
        return RequestHolder.getRequest();
    }

    /**
     * 获取header参数
     *
     * @return
     * @author yxkong
     * @createDate 2018年8月17日
     * @updateDate 2018年8月17日
     */
    public HashMap<String, String> getHeaderParam() {
        // 将请求头信息封装进map
        HashMap<String, String> headParams = new HashMap<String, String>();
        headParams.put("token", getToken());
        headParams.put("tenant", getTenant());
        // 添加当前时间
        headParams.put("t", getHeader("t"));
        headParams.put("_t", getHeader("t"));
        // 返回结果
        return headParams;
    }
}
