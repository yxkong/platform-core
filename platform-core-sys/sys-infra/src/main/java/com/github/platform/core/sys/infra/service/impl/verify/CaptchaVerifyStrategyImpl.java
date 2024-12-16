package com.github.platform.core.sys.infra.service.impl.verify;

import com.github.platform.core.standard.exception.InfrastructureException;
import com.github.platform.core.sys.domain.constant.CaptchaTypeEnum;
import com.github.platform.core.sys.domain.dto.VerifyCodeResult;
import com.github.platform.core.sys.domain.model.sms.VerifyEntity;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.domain.service.VerifyStrategy;
import com.github.platform.core.sys.infra.service.ICaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 图形验证策略
 *
 * @author: yxkong
 * @date: 2023/1/4 2:10 PM
 * @version: 1.0
 */
@Service("captchaVerifyStrategy")
public class CaptchaVerifyStrategyImpl implements VerifyStrategy {
    @Autowired
    private ICaptchaService captchaService;


    @Override
    public VerifyCodeResult getCode(UserEntity user) {
        return captchaService.createImage(null, CaptchaTypeEnum.MATH);
    }

    @Override
    public void verify(VerifyEntity verifyEntity)throws InfrastructureException {
        captchaService.check(null, verifyEntity.getVerifySeq(), verifyEntity.getVerifyCode());
    }

    /**
     * 验证收尾
     *
     * @param verifyEntity
     * @return
     */
    @Override
    public void finish(VerifyEntity verifyEntity) {
        //
    }

}
