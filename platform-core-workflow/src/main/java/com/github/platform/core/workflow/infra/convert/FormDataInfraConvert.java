package com.github.platform.core.workflow.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.common.entity.FormDataBase;
import com.github.platform.core.workflow.domain.context.FormDataContext;
import com.github.platform.core.workflow.domain.context.FormDataQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;
/**
* 表单数据基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:23.341
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormDataInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<FormDataDto> toDtos(List<FormDataBase> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            
    })
    FormDataDto toDto(FormDataBase entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<FormDataDto> ofPageBean(PageInfo<FormDataBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    FormDataBase toFormDataBase(FormDataQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    @Mappings({
            @Mapping(target = "tenantId", source = "context.tenantId", defaultExpression = "java(com.github.platform.core.auth.util.LoginUserInfoUtil.getTenantId())"),
            @Mapping(target = "createBy", source = "context.createBy", defaultExpression = "java(com.github.platform.core.auth.util.LoginUserInfoUtil.getLoginName())"),
            @Mapping(target = "updateBy", source = "context.updateBy", defaultExpression = "java(com.github.platform.core.auth.util.LoginUserInfoUtil.getLoginName())"),
    })
    FormDataBase toFormDataBase(FormDataContext context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */

    FormDataBase toFormDataBase(FormDataDto dto);

    /**
     * 自定义转换
     * @param datas
     * @param instanceNo
     * @return
     */
    default List<FormDataBase> toFormDatas(List<FormDataContext> datas, String instanceNo){
        ArrayList<FormDataBase> result = new ArrayList<>();
        for (FormDataContext item : datas) {
            FormDataBase formDataBase = toFormDataBase(item);
            formDataBase.setInstanceNo(instanceNo);
            result.add(formDataBase);
        }
        return result;
    }

    FormDataDto info2Data(FormInfoDto s);
}