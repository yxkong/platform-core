package com.github.platform.core.sys.domain.service;

import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;


public abstract class AbstractService {

    protected ResultBean<?> buildSimpleResp(Boolean res) {
        return Boolean.TRUE.equals(res) ? ResultBeanUtil.success() : ResultBeanUtil.fail("操作失败", null);
    }
}
