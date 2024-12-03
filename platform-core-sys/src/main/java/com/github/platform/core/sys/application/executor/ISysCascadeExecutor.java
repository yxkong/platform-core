package com.github.platform.core.sys.application.executor;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sys.domain.context.SysCascadeContext;
import com.github.platform.core.sys.domain.context.SysCascadeQueryContext;
import com.github.platform.core.sys.domain.dto.SysCascadeDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 级联表执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-01 20:57:44.667
 * @version 1.0
 */
public interface ISysCascadeExecutor {
    /**
    * 查询级联表列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysCascadeDto> query(SysCascadeQueryContext context);

    /**
     * 全量列表查询
     * @param context
     * @return
     */
    List<SysCascadeDto> list(SysCascadeQueryContext context);
    /**
     * 树形结构查询
     * @param context
     * @return
     */
    List<TreeSelectDto> tree(SysCascadeQueryContext context);
    /**
    * 新增级联表
    * @param context 新增上下文
    */
    String insert(SysCascadeContext context);
    /**
    * 根据id查询级联表明细
    * @param id 主键
    * @return 单条记录
    */
    SysCascadeDto findById(Long id);
    /**
    * 修改级联表
    * @param context 更新上下文
    */
    void update(SysCascadeContext context);
    /**
    * 根据id删除级联表记录
    * @param id 主键
    */
    void delete(Long id);



}