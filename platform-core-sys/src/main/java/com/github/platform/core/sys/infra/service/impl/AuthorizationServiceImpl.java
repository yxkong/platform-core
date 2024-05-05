package com.github.platform.core.sys.infra.service.impl;

import com.github.platform.core.auth.service.IAuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 授权实现
 * @author: yxkong
 * @date: 2024/4/26 21:38
 * @version: 1.0
 */
@Service
@Slf4j
public class AuthorizationServiceImpl implements IAuthorizationService {

    @Override
    public String bearer(String secretKey) {
        return null;
    }

    @Override
    public String basic(String user, String pwd) {
        return null;
    }
}
