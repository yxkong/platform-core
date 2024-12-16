package com.github.platform.core.sys.application.executor;

import com.github.platform.core.sys.domain.context.SysDeptContext;
import com.github.platform.core.sys.domain.context.SysDeptQueryContext;
import com.github.platform.core.sys.domain.dto.SysDeptDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;

import java.util.List;

/**
 * 部门执行器
 * @author: yxkong
 * @date: 2023/12/27 11:24
 * @version: 1.0
 */
public interface ISysDeptExecutor {
    List<SysDeptDto> query(SysDeptQueryContext context);

    void insert(SysDeptContext deptContext);

    void modify(SysDeptContext deptContext);

    void delete(Long id);

    List<TreeSelectDto> deptTree();
}
