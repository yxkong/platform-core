package com.github.platform.core.workflow.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.workflow.domain.common.entity.FormInfoBase;
import com.github.platform.core.workflow.domain.context.FormInfoContext;
import com.github.platform.core.workflow.domain.context.FormInfoQueryContext;
import com.github.platform.core.workflow.domain.dto.FormDataDto;
import com.github.platform.core.workflow.domain.dto.FormInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;
/**
* 表单信息基础设施层转换器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-11-17 18:23:21.269
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormInfoInfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<FormInfoDto> toDtos(List<FormInfoBase> list);
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<FormDataDto> toDataList(List<FormInfoBase> list);
    default FormDataDto toDataDto(FormInfoBase entity){
        return FormDataDto.builder()
                .name(entity.getName())
                .label(entity.getLabel())
                .type(entity.getType())
                .sort(entity.getSort())
                .build();
    }
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
            
    })
    FormInfoDto toDto(FormInfoBase entity);
    /**
     * 数据库实体转dto
     * @param entity 数据库实体
     * @return dto
     */
    @Mappings({
            @Mapping(target = "id", expression = "java(null)"),
            @Mapping(target = "createBy", expression = "java(null)"),
            @Mapping(target = "updateBy", expression = "java(null)"),
            @Mapping(target = "createTime", expression = "java(null)"),
            @Mapping(target = "updateTime", expression = "java(null)"),
            @Mapping(target = "remark", expression = "java(null)"),
    })
    FormInfoDto toFormView(FormInfoBase entity,Integer x);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<FormInfoDto> ofPageBean(PageInfo<FormInfoBase> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    FormInfoBase toFormInfoBase(FormInfoQueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    @Mappings({
            @Mapping(target = "status", source = "context.status",defaultValue = "1"),
            @Mapping(target = "tenantId", source = "context.tenantId", defaultExpression = "java(com.github.platform.core.auth.util.LoginUserInfoUtil.getTenantId())"),
            @Mapping(target = "createBy", source = "context.createBy", defaultExpression = "java(com.github.platform.core.auth.util.LoginUserInfoUtil.getLoginName())"),
            @Mapping(target = "updateBy", source = "context.updateBy", defaultExpression = "java(com.github.platform.core.auth.util.LoginUserInfoUtil.getLoginName())"),
    })
    FormInfoBase toFormInfoBase(FormInfoContext context);


    @Mappings({
            @Mapping(target = "formNo", source = "formNo"),
    })
    default List<FormInfoBase> toFormInfos(List<FormInfoContext> context, String formNo) {
        ArrayList<FormInfoBase> result = new ArrayList<>();
        for (FormInfoContext item : context) {
            FormInfoBase formInfoBase = toFormInfoBase(item);
            formInfoBase.setFormNo(formNo);
            result.add(formInfoBase);
        }
        return result;
    }
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    FormInfoBase toFormInfoBase(FormInfoDto dto);
}