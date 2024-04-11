package com.github.platform.core.code.infra.convert;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.code.domain.common.entity.CodeGenConfigBase;
import com.github.platform.core.code.domain.context.GenConfigContext;
import com.github.platform.core.code.domain.dto.GenConfigDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;

import java.util.List;

/**
 * 代码生成转化
 *
 * @Author: yxkong
 * @Date: 2023/4/25 2:09 PM
 * @version: 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenInfraConvert {

    /**
     * 数据库实体列表转dto列表
     * @param list 数据库列表
     * @return dto列表
     */
    List<GenConfigDto> toEntity(List<CodeGenConfigBase> list);

    @Mappings({
            @Mapping(target = "totalSize", source = "total"),
            @Mapping(target = "data", source = "list"),
    })
    PageBean<GenConfigDto> ofPageGen(PageInfo<CodeGenConfigBase> pageInfo);
    @Mappings({
            @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(genConfig.getId()))"),
            @Mapping(target = "packageName", source = "packageName",defaultValue = "com.github.platform.core"),
            @Mapping(target = "author", source = "author",defaultValue = "yxkong"),
            @Mapping(target = "path", source = "path",defaultValue = "src.views"),
            @Mapping(target = "apiPath", source = "apiPath",defaultValue = "src.api"),
    })
    GenConfigDto toDto(CodeGenConfigBase genConfig);

    CodeGenConfigBase toConfigDo(GenConfigContext context);
}
