package com.github.platform.core.standard.entity.common;

import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 通用登录信息
 * @author: yxkong
 * @date: 2023/3/27 4:06 PM
 * @version: 1.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo implements Serializable {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    protected Long id;
    /**
     * 用户uuid,备用
     */
    @Schema(description = "用户uuid")
    protected String uuid;

    /**
     * 登录名(在某些场景下登录名为手机号)
     */
    @Schema(description = "登录名，在某些场景下登录名为手机号")
    protected String loginName;
    /**
     * 用户名,或用户姓名
     */
    @Schema(description = "用户名,或用户姓名")
    protected String userName;
    /**
     * 年龄
     */
    @Schema(description = "年龄")
    protected Integer age;
    /**
     * 租户
     */
    @Schema(description = "租户")
    protected Integer tenantId;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    protected String mobile;

    /**
     * 登录方式 normal,普通登录； ldap,ldap登录;sms,短信登录；thirdWx，三方微信登录;thirdApliy,三方支付宝登录；
     */
    @Schema(description = "登录方式")
    protected String loginWay;
    /**登录ip*/
    protected String requestIp;
    /**浏览器*/
    protected String browser;
    /**操作系统*/
    protected String os;
    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    protected String loginTime;
    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    protected String registerTime;

    /**
     * 用户token
     */
    @Schema(description = "用户token")
    protected String token;
    /**登录状态，兼容登录失败以后发送事件*/
    protected String status;
    /**登录失败的原因，兼容登录失败以后发送事件*/
    protected String message;
    public boolean isSuc(){
        return ResultStatusEnum.SUCCESS.getStatus().equals(this.status);
    }
}
