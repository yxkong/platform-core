package com.github.platform.core.standard.entity.event;

import com.github.platform.core.standard.entity.common.LoginInfo;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * 领域事件
 * @Author: yxkong
 * @Date: 2024/12/16
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DomainEvent implements Serializable {
    /**
     * 消息发送的时间戳
     */
    @Schema(description = "消息发送的时间戳")
    @Builder.Default
    protected String sendTime = LocalDateTimeUtil.dateTimeDefault();
    /**
     * 消息唯一标识，如果没有data进行md5
     */
    @Schema(description = "消息唯一标识，如果没有data进行md5")
    protected String msgId;
    /**
     * 消息体
     */
    @Schema(description = "消息体")
    protected Object data;
    /**
     * 消息状态，1成功，0，失败
     */
    @Schema(description = "消息状态，1成功，0，失败")
    private Integer status;
    /**
     * 提示消息
     */
    @Schema(description = "提示消息")
    private String msg;
    @Schema(description = "消息的租户")
    private Integer tenantId;

    private UserInfo userInfo = new UserInfo();
    private AppInfo appInfo = new AppInfo();
    private HandlerInfo handlerInfo = new HandlerInfo();


    public void addUserInfo(LoginInfo loginInfo){
        this.addUserInfo(loginInfo.getId(),loginInfo.getLoginName(),loginInfo.getLoginName(),null,loginInfo.getTenantId());
    }
    /**
     * 添加用户信息
     */
    public void addUserInfo(Long userId, String loginName, Integer tenantId){
       this.addUserInfo(userId,loginName,null,null,tenantId);
    }
    /**
     * 添加用户信息
     */
    public void addUserInfo(Long userId, String loginName, String mobile, Integer tenantId){
        this.addUserInfo(userId,loginName,mobile,null,tenantId);
    }
    /**
     * 添加用户信息
     */
    public void addUserInfo(Long userId, String loginName, String mobile,String certId, Integer tenantId){
        this.userInfo.userId = userId;
        this.userInfo.loginName = loginName;
        this.userInfo.tenantId = tenantId;
        this.userInfo.mobile = mobile;
        this.userInfo.certId = certId;
    }

    /**
     * 添加应用信息
     */
    public void addAppInfo(String serviceName,String module,String node){
        this.appInfo.serviceName = serviceName;
        this.appInfo.module = module;
        this.appInfo.node = node;
    }
    public void addHandlerInfo(String targetService,String handlerBean){
        this.handlerInfo.targetService = targetService;
        this.handlerInfo.handlerBean = handlerBean;
    }
    @Getter
    public static class UserInfo{
        /**用户唯一标识*/
        @Schema(description = "用户唯一标识")
        private Long userId;
        /**登录账户*/
        @Schema(description = "登录账户")
        private String loginName;
        /**手机号*/
        @Schema(description = "手机号")
        private String mobile;
        /**身份证号*/
        @Schema(description = "身份证号")
        private String certId;
        /** 租户*/
        @Schema(description = "租户")
        private Integer tenantId;
    }

    /**
     * 应用信息
     */
    @Getter
    public static class AppInfo{
        /** 来源的项目名称,使用驼峰，英文*/
        @Schema(description = "来源的项目名称")
        private String serviceName;
        /**
         * 消息所属的业务模块，exp: user
         */
        @Schema(description = "消息所属的业务模块")
        private String module;
        /**
         * 消息节点，比如用户：login,register 等
         */
        @Schema(description = "消息节点")
        protected String node;
    }
    /**
     * 处理信息
     */
    @Getter
    public static class HandlerInfo{
        /**目标项目名称,使用驼峰，英文*/
        @Schema(description = "目标项目名称")
        private String targetService;
        /**事件处理器，最后事件由哪个处理器处理*/
        @Schema(description = "事件处理器，最后事件由哪个处理器处理")
        private String handlerBean;
    }
}
