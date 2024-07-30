package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.FormDataQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
* 表单数据网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:23.341
* @version 1.0
*/
public interface IFormDataGateway {
    /**
    * 查询表单数据列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<FormDataDto> query(FormDataQueryContext context);
    /**
    * 新增表单数据
    * @param context 新增上下文
    * @return 返回结果
    */
    FormDataDto insert(FormDataContext context);

    /**
    * 批量新增表单数据
    * @param infos 新增上下文
    * @param instanceNo 实例编号
    */
    void insertList(List<FormDataContext> infos, String instanceNo);

    /**
     * 批量更新
     * @param infos
     */
    void updateList(List<FormDataContext> infos, String instanceNo);
    /**
    * 根据id查询表单数据明细
    * @param id 主键
    * @return 结果
    */
    FormDataDto findById(Long id);
    /**
    * 修改表单数据
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, FormDataDto> update(FormDataContext context);
    /**
    * 根据id删除表单数据记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    /**
     * 根据流程实例和表单获取 表单数据
     * @param instanceNo 实例编号
     * @param formNo  表单编号
     * @return
     */
    List<FormDataDto> findByInstanceNoAndFormNo(String instanceNo, String formNo);
}
