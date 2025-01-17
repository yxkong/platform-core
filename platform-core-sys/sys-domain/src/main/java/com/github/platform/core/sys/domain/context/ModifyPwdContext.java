package com.github.platform.core.sys.domain.context;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author yxkong
 * @create 2023/2/10 上午11:08
 * @desc ModifyPwdContext
 */
@ToString(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ModifyPwdContext extends TenantContext {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 旧密码
     */
    private String oldPwd;

    /**
     * 新密码
     */
    private String newPwd;
}
