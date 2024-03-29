package com.github.platform.core.sys.domain.model.user;

import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import lombok.Data;

import java.time.LocalDate;

/**
 * 三方用户实体
 * @author: yxkong
 * @date: 2023/5/31 10:03 AM
 * @version: 1.0
 */
@Data
public class ThirdUserEntity {
    /**登录名称*/
    private String loginName;
    /**三方唯一表示*/
    private String openId;
    /**用户名称*/
    private String userName;
    /**用户昵称*/
    private String nickName;
    /**加入时间*/
    private LocalDate joinDate;
    /**用户邮箱*/
    private String email;
    /**员工类型，1正式，0外包*/
    private Integer userType;

    private String remark;

    /**租户*/
    private Integer tenantId;
    /**用户来源*/
    private UserChannelEnum channel;
}
