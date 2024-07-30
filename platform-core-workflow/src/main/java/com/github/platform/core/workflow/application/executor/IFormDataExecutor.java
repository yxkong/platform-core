package com.github.platform.core.workflow.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.FormDataQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import com.github.platform.core.workflow.domain.dto.ProcessInstanceDto;

import java.util.List;

/**
 * 表单数据执行器
 * @author: yxkong
 * @date: 2023/12/27 11:33
 * @version: 1.0
 */
public interface IFormDataExecutor {
    /**
     * 查询表单数据列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<FormDataDto> query(FormDataQueryContext context);

    /**
     * 新增表单数据
     *
     * @param context 新增上下文
     */
    void insert(FormDataContext context);

    /**
     * 根据id查询表单数据明细
     *
     * @param id 主键
     * @return 单条记录
     */
    FormDataDto findById(Long id);

    /**
     * 修改表单数据
     *
     * @param context 更新上下文
     */
    void update(FormDataContext context);

    /**
     * 根据id删除表单数据记录
     *
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 表单数据数据
     * @param taskFormInfo 表单数据
     * @param instanceNo 实例编号
     * @param formInstNo 表单实例编号（为空）
     */
    void formDataHandler(List<FormDataContext> taskFormInfo,String instanceNo, String formInstNo);

    /**
     * 获取表单数据
     * @param instanceDto
     * @param isMain
     * @param formKey
     * @return
     */
    List<FormInfoDto> getFormInfoWithData(ProcessInstanceDto instanceDto, boolean isMain, String formKey);
}
