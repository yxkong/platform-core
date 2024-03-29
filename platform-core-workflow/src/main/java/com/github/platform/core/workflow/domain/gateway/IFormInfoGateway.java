package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.workflow.domain.common.entity.FormInfoBase;
import com.github.platform.core.workflow.domain.context.FormInfoContext;
import com.github.platform.core.workflow.domain.context.FormInfoQueryContext;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
* 表单信息网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:21.269
* @version 1.0
*/
public interface IFormInfoGateway {
    /**
    * 查询表单信息列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<FormInfoDto> query(FormInfoQueryContext context);
    /**
    * 新增表单信息
    * @param context 新增上下文
    * @return 返回结果
    */
    FormInfoDto insert(FormInfoContext context);
    /**
    * 根据id查询表单信息明细
    * @param id 主键
    * @return 结果
    */
    FormInfoDto findById(Long id);
    /**
    * 修改表单信息
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, FormInfoDto> update(FormInfoContext context);
    /**
    * 根据id删除表单信息记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    /**
     * 批量插入
     * @param infos
     */
    void insertList(List<FormInfoContext> infos, String formNo);

    /**
     * 根据表单编号查询表单信息
     * @param formNo
     * @return
     */
    List<FormInfoDto> findByFromNo(String formNo);

    /**
     * 批量更新
     * @param updates
     */
    void updateList(List<FormInfoContext> updates);
}
