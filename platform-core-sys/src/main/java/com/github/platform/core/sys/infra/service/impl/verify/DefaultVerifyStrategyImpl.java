package com.github.platform.core.sys.infra.service.impl.verify;

import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.application.dto.VerifyCodeResult;
import com.github.platform.core.sys.domain.model.sms.VerifyEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.domain.service.VerifyStrategy;
import org.springframework.stereotype.Service;

/**
 * 默认验证策略
 *
 * @author: yxkong
 * @date: 2024/1/4 9:10 PM
 * @version: 1.0
 */
@Service("defaultVerifyStrategy")
public class DefaultVerifyStrategyImpl implements VerifyStrategy {

    @Override
    public VerifyCodeResult getCode(UserEntity user) {
        return null;
    }

    @Override
    public void verify(VerifyEntity verifyEntity)throws InfrastructureException {
    }

    @Override
    public void finish(VerifyEntity verifyEntity) {
        //
    }

}
