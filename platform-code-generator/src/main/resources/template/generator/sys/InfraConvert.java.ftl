package ${infraPackage}.convert;

import com.github.pagehelper.PageInfo;
import ${domainPackage}.common.entity.${entityName}Base;
import ${domainPackage}.context.${entityName}Context;
import ${domainPackage}.context.${entityName}QueryContext;
import ${domainPackage}.dto.${entityName}Dto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.mapstruct.*;
import java.util.List;
/**
 * ${apiAlias}基础设施层转换器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ${entityName}InfraConvert {
    /**
    * 数据库实体列表转dto列表
    * @param list 数据库实体列表
    * @return dto列表
    */
    List<${entityName}Dto> toDtos(List<${entityName}Base> list);
    /**
    * 数据库实体转dto
    * @param entity 数据库实体
    * @return dto
    */
    @Mappings({
        @Mapping(target = "strId", expression = "java(com.github.platform.core.common.utils.SignUtil.getStrId(entity.getId()))"),
    })
    ${entityName}Dto toDto(${entityName}Base entity);
    /**
    * 数据库分页转业务分页
    * @param pageInfo 数据库分页
    * @return 业务分页
    */
    @Mappings({
        @Mapping(target = "totalSize", source = "total"),
        @Mapping(target = "data", source = "list"),
    })
    PageBean<${entityName}Dto> ofPageBean(PageInfo<${entityName}Base> pageInfo);

    /**
    * 查询上下文转数据库实体
    * @param context 数据库分页
    * @return 数据库实体
    */
    ${entityName}Base to${entityName}Base(${entityName}QueryContext context);
    /**
    * 实体上下文转数据库实体
    * @param context 实体上下文
    * @return 数据库实体
    */
    ${entityName}Base to${entityName}Base(${entityName}Context context);
    /**
    * dto转数据库实体
    * @param dto 传输实体
    * @return 数据库实体
    */
    ${entityName}Base to${entityName}Base(${entityName}Dto dto);
}