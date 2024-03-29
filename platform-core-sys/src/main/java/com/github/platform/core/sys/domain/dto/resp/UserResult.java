package com.github.platform.core.sys.domain.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息
 *
 * @author wangxiaozhou
 * @create 2023/2/8 下午3:40
 * @desc UserVO
 */
@Schema(description = "用户信息")
@Data
public class UserResult {

    /**
     * 用户ID
     */
    @Schema(description ="用户ID")
    private Long id;

    /**
     * 登陆名称
     */
    @Schema(description ="登陆名称")
    private String loginName;

    /**
     * 用户姓名
     */
    @Schema(description ="用户姓名")
    private String userName;
    /**
     * 用户邮箱
     */
    @Schema(description ="邮箱")
    private String email;
    /**
     * 手机号
     */
    @Schema(description ="手机号")
    private String mobile;
    /**
     * 租户号
     */
    @Schema(description ="租户号")
    private Integer tenantId;
    /**
     * 来源渠道：注册（reg）、后台添加(add)、ldap、钉钉（ding）、微信（wx）、qq
     */
    private String channel;
    /**
     * 租户号
     */
    @Schema(description ="租户名称")
    private String tenantName;
    /**
     * 部门名称
     */
    @Schema(description ="部门名称")
    private String deptName;

    /**
     * 部门ID
     */
    @Schema(description ="部门ID")
    private Long deptId;

    /**
     * 创建时间
     */
    @Schema(description ="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 用户状态
     */
    @Schema(description ="用户状态")
    private Integer status;

    /**
     * 用户角色ID列表
     */
    @Schema(description ="用户角色ID列表,使用逗号分割")
    private String roleIdStr;

    /**
     * 用户角色ID列表
     */
    @Schema(description ="用户角色ID列表")
    private List<Long> roleIds;
    @Schema(description ="用户角色名称列表")
    private String roleNameStr;


}
