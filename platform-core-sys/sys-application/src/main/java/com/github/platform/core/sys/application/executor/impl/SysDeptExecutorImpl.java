package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.sys.application.constant.SysAppResultEnum;
import com.github.platform.core.sys.application.executor.ISysDeptExecutor;
import com.github.platform.core.sys.domain.context.SysDeptContext;
import com.github.platform.core.sys.domain.context.SysDeptQueryContext;
import com.github.platform.core.sys.domain.dto.SysDeptDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
import com.github.platform.core.sys.domain.gateway.ISysDeptGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 部门执行器
 *
 * @author: yxkong
 * @date: 2023/2/7 5:32 下午
 * @version: 1.0
 */
@Service
@Slf4j
public class SysDeptExecutorImpl extends SysExecutor implements ISysDeptExecutor {
    @Resource
    private ISysDeptGateway deptGateway;

    @Override
    public List<SysDeptDto> query(SysDeptQueryContext context) {
        context.setTenantId(getTenantId(context));
        if (Objects.isNull(context.getTenantId())){
            throw exception(SysAppResultEnum.NO_TENANT);
        }
        return deptGateway.queryList(context);
    }


    @Override
    public void insert(SysDeptContext context) {
        context.setTenantId(getTenantId(context));
        deptGateway.insert(context);
    }

    @Override
    public void modify(SysDeptContext context) {
        context.setTenantId(getTenantId(context));
        deptGateway.update(context);
    }


    @Override
    public void delete(Long id) {
        deptGateway.delete(id);
    }

    @Override
    public List<TreeSelectDto> deptTree() {
        return deptGateway.deptTree();
    }

}
