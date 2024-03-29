package com.github.platform.core.sys.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.utils.EncryptUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.annotation.DomainEntity;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.util.MD5Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 用户实体
 * @author: yxkong
 * @date: 2023/1/4 10:34 AM
 * @version: 1.0
 */
@DomainEntity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    /**表主键*/
    private Long id;

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

    /**
     * 租户
     */
    private Integer tenantId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 盐值
     */
    @JsonIgnore
    private String salt;

    /**
     * md5密码
     */
    @JsonIgnore
    private String md5Pwd;

    /**
     * 帐号状态
     */
    private Integer status;
    /**
     * 注册时间
     */
    private LocalDateTime registerTime;
    /**
     * 默认角色id，如果存在，表示走的默认角色
     */
    private Set<Long> defaultRoles;

    /**
     * 判断用户输入密码和库里报错的密码是否一致
     * @param userPwd 用户输入的密码
     * @return
     */
    @JsonIgnore
    public boolean login(String userPwd){
        return EncryptUtil.me.verifyPwd(this.salt,userPwd,this.md5Pwd);
    }

    /**
     * 是否禁用
     * @return
     */
    @JsonIgnore
    public boolean disable(){
        return StatusEnum.OFF.getVal().equals(this.getStatus());
    }

}

