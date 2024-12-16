package com.github.platform.core.sys.domain.dto.resp;

import com.github.platform.core.sys.domain.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录返回体，内部使用
 * @author: yxkong
 * @date: 2022/12/30 9:22 AM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {

    /**登录token */
    private String token;
    /**登录后的信息*/
    private UserEntity userEntity;


}
