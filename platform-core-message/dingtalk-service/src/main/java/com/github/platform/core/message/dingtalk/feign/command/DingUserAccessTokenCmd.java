package com.github.platform.core.message.dingtalk.feign.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 钉钉获取用户token访问实体
 * @author: yxkong
 * @date: 2024/4/1 15:19
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DingUserAccessTokenCmd {
    /**应用id。可使用扫码登录应用或者第三方个人小程序的appId
     * <br> 企业内部应用传应用的AppKey
     * <br> 第三方企业应用传应用的SuiteKey
     * <br> 第三方个人应用传应用的AppId
     */
    private String clientId;
    /**应用密钥
     * <br> 企业内部应用传应用的AppSecret
     * <br> 第三方企业应用传应用的SuiteSecret
     * <br> 第三方个人应用传应用的AppSecret
     */
    private String clientSecret;
    /**OAuth 2.0 临时授权码，根据获取登录用户的访问凭证内容，获取临时授权码authCode*/
    private String code;
    /**OAuth2.0刷新令牌，从返回结果里面获取*/
    private String refreshToken;
    /**如果使用授权码换token，传authorization_code,如果使用刷新token换用户token，传refresh_token*/
    private String grantType = "authorization_code";

    public DingUserAccessTokenCmd(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.grantType = "authorization_code";
    }
}
