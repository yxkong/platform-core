package com.github.platform.core.common.service;

import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.exception.BaseResult;
import com.github.platform.core.standard.util.ResultBeanUtil;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 构建返回
 *
 * @author: yxkong
 * @date: 2023/9/6 10:19 AM
 * @version: 1.0
 */
public class BaseBizCommon {
    protected ResultBean buildSucResp() {
        return ResultBeanUtil.success();
    }
    protected ResultBean buildResp(BaseResult baseResult) {
        return ResultBeanUtil.result(baseResult);
    }
    protected ResultBean buildFailResp() {
        return ResultBeanUtil.fail("操作失败", null);
    }
    protected <T> ResultBean<T> buildSucResp(T data) {
        return ResultBeanUtil.success(data);
    }

    protected <T> ResultBean<T> buildSucResp(String msg,T data) {
        return ResultBeanUtil.succ(msg,data);
    }
    protected ResultBean buildSimpleResp(Boolean res) {
        return Boolean.TRUE.equals(res) ? ResultBeanUtil.success() : ResultBeanUtil.fail("操作失败", null);
    }
    protected ResultBean buildSimpleResp(Boolean res,String msg) {
        return Boolean.TRUE.equals(res) ? ResultBeanUtil.success(msg,null) : ResultBeanUtil.fail(msg, null);
    }
    protected ResultBean buildSimpleResp(Pair<Boolean,String> pair) {
        return buildSimpleResp(pair.getKey(),pair.getValue());
    }
}
