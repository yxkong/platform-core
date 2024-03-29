package com.github.platform.core.workflow.application.executor.strategy;

import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.domain.context.UserQueryContext;

import java.util.List;

/**
 * 用户策略
 * @Author: yxkong
 * @Date: 2023/12/11 18:57
 * @version: 1.0
 */
public interface UserStrategy {

    /**
     * 根据角色获取用户
     * @param userQuery
     * @return
     */
    List<OptionsDto> users(UserQueryContext userQuery);
}
