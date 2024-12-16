package com.github.platform.core.sys.domain.model;

import com.github.platform.core.sys.domain.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录缓存信息
 * @author: yxkong
 * @date: 2023/1/5 11:06 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginCacheModel {
    /**登录用户信息*/
    private UserEntity userEntity;
}
