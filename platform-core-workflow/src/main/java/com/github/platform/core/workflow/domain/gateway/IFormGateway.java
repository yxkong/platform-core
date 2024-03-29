package com.github.platform.core.workflow.domain.gateway;

import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.workflow.domain.context.FormContext;
import com.github.platform.core.workflow.domain.context.FormQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDetailDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.dto.FormDto;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
* 表单配置网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
public interface IFormGateway {
    /**
    * 查询表单配置列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<FormDto> query(FormQueryContext context);
    /**
    * 新增表单配置
    * @param context 新增上下文
    * @return 返回结果
    */
    FormDto insert(FormContext context);
    /**
    * 根据id查询表单配置明细
    * @param id 主键
    * @return 结果
    */
    FormDto findById(Long id);
    /**
    * 修改表单配置
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, FormDto> update(FormContext context);
    /**
    * 根据id删除表单配置记录
    * @param id 主键id
    * @return 删除结果
    */
    int delete(Long id);

    List<FormDto> allForms(StatusEnum status);
}
