package com.github.platform.core.sys.domain.model.user;

import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 三方用户实体
 * @author: yxkong
 * @date: 2023/5/31 10:03 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdUserEntity {
    /**登录名称*/
    private String loginName;
    /**三方唯一标识，钉钉为userId,ldap为登录账户*/
    private String openId;
    /**联合id*/
    private String unionId;
    /**用户名称*/
    private String userName;
    /**用户昵称*/
    private String nickName;
    /**加入时间*/
    private LocalDate joinDate;
    /**用户邮箱*/
    private String email;
    /**用户手机号*/
    private String mobile;
    /**员工类型，1正式，0外包*/
    private Integer userType;

    private String remark;

    /**租户*/
    private Integer tenantId;
    /**用户来源*/
    private UserChannelEnum channel;
}
