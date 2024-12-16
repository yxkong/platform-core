package com.github.platform.core.sys.domain.model.event;

import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 登录实体
 * @author: yxkong
 * @date: 2023/5/30 6:37 PM
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginEntity {
    private String loginName;
    private LoginWayEnum loginWay;
    private String token;
    private String status;
    private String message;
    private String requestIp;
    private String browser;
    private String os;
    private LocalDateTime loginTime;
}
