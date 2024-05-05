package com.github.platform.core.sys.domain.context;

import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.constant.UserLogBizTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 注册上下文
 *
 * @author: yxkong
 * @date: 2023/1/3 11:09 AM
 * @version: 1.0
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterContext extends TenantContext {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户手机号码
     */
    private String mobile;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 接口密钥
     */
    private String secretKey;

    /**
     * 用户登录名
     */
    private String loginName;
    /**
     * 来源：注册（reg）、后台添加(add)、ldap、钉钉（ding）、微信（wx）、qq
     */
    private UserChannelEnum channel;
    /**
     * 日志记录类型
     */
    private UserLogBizTypeEnum logBizTypeEnum;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 角色列表
     */
    private Set<String> roleKeys;

    private String createBy;
    private LocalDateTime createTime;

}
