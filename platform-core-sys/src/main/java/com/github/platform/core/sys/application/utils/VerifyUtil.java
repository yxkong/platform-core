package com.github.platform.core.sys.application.utils;

import com.github.platform.core.standard.exception.BaseResult;
import com.github.platform.core.sys.domain.dto.resp.VerifyResult;

/**
 * 验证工具类
 * @author: yxkong
 * @date: 2023/1/4 2:40 PM
 * @version: 1.0
 */
public class VerifyUtil {
    public static VerifyResult result(Boolean status, BaseResult verifyResult){
        return new VerifyResult(status,verifyResult);
    }
}
