package com.github.platform.core.standard.exception;

/**
 * 状态码枚举基类
 * @author yxkong
 * @date 2019/5/27-19:24
 */
public interface BaseResult {

    /**
     * 获取状态码
     * @return
     */
    String getStatus();

    /**
     * 获取message
     * @return
     */
    String getMessage();
}
