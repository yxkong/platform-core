package com.github.platform.core.sys.domain.model.user;

import com.github.platform.core.standard.annotation.DomainValueObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户值对象
 * @author: yxkong
 * @date: 2022/11/15 3:19 PM
 * @version: 1.0
 */
@DomainValueObject
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserObject {
    /**
     * 用户手机号码
     */
    private String mobile;
    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 用户名称
     */
    private String userName;

    /**租户*/
    private Integer tenantId;

    /**租户名称*/
    private String tenantName;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;
}
