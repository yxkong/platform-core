package com.github.platform.core.workflow.adapter.api.convert;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.workflow.adapter.api.command.FormCmd;
import com.github.platform.core.workflow.adapter.api.command.FormInfoCmd;
import com.github.platform.core.workflow.adapter.api.command.FormQuery;
import com.github.platform.core.workflow.domain.context.FormContext;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.FormInfoContext;
import com.github.platform.core.workflow.domain.context.FormQueryContext;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 表单配置Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:18.130
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    FormQueryContext toQuery(FormQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    FormContext toContext(FormCmd cmd);

    /**
     * formInfo转
     * @param formInfoDto
     * @return
     */
    FormDataContext toContext(FormInfoDto formInfoDto);

    List<FormInfoContext> toInfos(List<FormInfoCmd> infos);
    default List<FormDataContext> toDatas(List<FormInfoDto> formInfos,Map<String, String> formData){
        List<FormDataContext> rst = new ArrayList<>();
        if (CollectionUtil.isEmpty(formInfos) || CollectionUtil.isEmpty(formData)){
            return rst;
        }
        formInfos.forEach(s->{
            FormDataContext context = toContext(s);
            context.setValue(formData.getOrDefault(s.getName(),""));
            rst.add(context);
        });
        return rst;
    }
}