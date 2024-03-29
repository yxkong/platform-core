package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.context.FormInfoContext;
import com.github.platform.core.workflow.domain.context.FormInfoQueryContext;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;

import java.util.List;

/**
 * 表单信息执行器
 * @author: yxkong
 * @date: 2023/12/27 11:42
 * @version: 1.0
 */
public interface IFormInfoExecutor {
    /**
     * 查询表单信息列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<FormInfoDto> query(FormInfoQueryContext context);

    /**
     * 新增表单信息
     *
     * @param context 新增上下文
     */
    void insert(FormInfoContext context);

    /**
     * 根据id查询表单信息明细
     *
     * @param id 主键
     * @return 单条记录
     */
    FormInfoDto findById(Long id);

    /**
     * 修改表单信息
     *
     * @param context 更新上下文
     */
    void update(FormInfoContext context);

    /**
     * 根据id删除表单信息记录
     *
     * @param id 主键
     */
    void delete(Long id);
    /**
     * 根据表单编号查询表单信息
     * @param formNo
     * @return
     */
    List<FormInfoDto> findByFromNo(String formNo);
}
