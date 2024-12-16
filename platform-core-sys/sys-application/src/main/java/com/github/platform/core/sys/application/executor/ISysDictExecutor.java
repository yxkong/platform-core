package com.github.platform.core.sys.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysDictContext;
import com.github.platform.core.sys.domain.context.SysDictQueryContext;
import com.github.platform.core.sys.domain.dto.SysDictDto;

import java.util.List;

/**
 * 字典执行器
 * @author: yxkong
 * @date: 2023/12/27 11:18
 * @version: 1.0
 */
public interface ISysDictExecutor {


    PageBean<SysDictDto> query(SysDictQueryContext context);

    void insert(SysDictContext context);

    void update(SysDictContext context);

    void delete(Long id);

    List<SysDictDto> findByType(SysDictQueryContext queryContext);
}
