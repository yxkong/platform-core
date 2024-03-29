package com.github.platform.core.sms.domain.entity;


/**
 * 短信路由规则实体,路由规则配置到短信模板上
 *
 * @author: yxkong
 * @date: 2022/6/23 10:12 AM
 * @version: 1.0
 */
@Deprecated
public class SmsRouteEntity {
    /**
     * 路由规则，如果是路由，routerRule = null或者是router，否则为具体的proNo
     */
    private String routerRule;
    /**
     * 短信模板编号 （t_sms_template）
     */
    private String tempNo;

    public SmsRouteEntity(String routerRule, String tempNo) {
        this.routerRule = routerRule;
        this.tempNo = tempNo;
    }

    public Boolean isRoute(){
        if (null == this.routerRule || "router".equalsIgnoreCase(routerRule)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public String getRouterRule() {
        return routerRule;
    }

    public void setRouterRule(String routerRule) {
        this.routerRule = routerRule;
    }

    public String getTempNo() {
        return tempNo;
    }

    public void setTempNo(String tempNo) {
        this.tempNo = tempNo;
    }
}
