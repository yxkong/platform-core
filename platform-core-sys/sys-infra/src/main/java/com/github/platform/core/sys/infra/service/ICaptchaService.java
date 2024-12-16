package com.github.platform.core.sys.infra.service;

import com.github.platform.core.sys.domain.constant.CaptchaTypeEnum;
import com.github.platform.core.sys.domain.dto.VerifyCodeResult;

/**
 * 图形验证码接口
 * @author: yxkong
 * @date: 2022/12/9 10:47 AM
 * @version: 1.0
 */
public interface ICaptchaService {
    /**
     * 创建图形验证码
     * @param bizNo 业务唯一编码，可以使用用户，也可以自己生成
     * @param captchaType 验证码生成类型
     * @return
     */
    VerifyCodeResult createImage(String bizNo, CaptchaTypeEnum captchaType);

    /**
     * 图形验证码检测(图形验证码验证一次就删除)
     * @param bizNo 业务唯一编码，可以使用用户，也可以自己生成
     * @param verifySeq
     * @param code
     * @return
     */
    Boolean check(String bizNo, String verifySeq, String code);

}
