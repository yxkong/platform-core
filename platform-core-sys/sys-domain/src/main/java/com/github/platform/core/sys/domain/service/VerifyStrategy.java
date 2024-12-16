package com.github.platform.core.sys.domain.service;

import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.domain.dto.VerifyCodeResult;
import com.github.platform.core.sys.domain.model.sms.VerifyEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;

/**
 * 验证服务
 *
 * @author: yxkong
 * @date: 2023/1/4 1:59 PM
 * @version: 1.0
 */
public interface VerifyStrategy {

    /**
     * 获取验证码
     * 1，sms 需要先校验图形的结果
     * 2，图形验证码直接获取
     * @param userEntity
     * @return
     */
    VerifyCodeResult getCode(UserEntity userEntity);

    /**
     * 验证
     *
     * @param verifyEntity
     * @return
     */
    void verify(VerifyEntity verifyEntity) throws InfrastructureException;

    /**
     * 验证收尾
     * @param verifyEntity
     */
    void finish(VerifyEntity verifyEntity);


}
