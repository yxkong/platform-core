package com.github.platform.core.workflow.application.executor.strategy.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.workflow.application.executor.strategy.UserStrategy;
import com.github.platform.core.workflow.domain.context.UserQueryContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户策略实现
 * @Author: yxkong
 * @Date: 2023/12/11 18:59
 * @version: 1.0
 */
@Service("sysUserStrategy")
public class SysUserStrategyImpl implements UserStrategy {
    @Resource
    private ISysUserGateway userGateway;
    @Override
    public List<OptionsDto> users(UserQueryContext userQuery) {
        List<OptionsDto> rst = new ArrayList<>();
        List<SysUserDto> userList = userGateway.findByRoleIds(userQuery.getRoles());
        if (CollectionUtil.isEmpty(userList)){
            return rst;
        }
        userList.forEach(s->{
            rst.add(new OptionsDto(s.getLoginName(),s.getUserName()));
        });
        return rst;
    }
}
