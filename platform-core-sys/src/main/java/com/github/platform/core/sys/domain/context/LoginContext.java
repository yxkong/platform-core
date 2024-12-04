package com.github.platform.core.sys.domain.context;

import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * 登录上下文
 *
 * @author: yxkong
 * @date: 2022/4/26 2:36 PM
 * @version: 1.0
 */
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LoginContext extends VerifyContext {
    private String mobile;
    private String loginName;
    private String pwd;
    private Integer tenantId;
    private LoginWayEnum loginWay;
}
