package ${adapterPackage}.api.convert;

import ${adapterPackage}.api.command.${entityName}Cmd;
import ${adapterPackage}.api.command.${entityName}Query;
import ${domainPackage}.context.${entityName}Context;
import ${domainPackage}.context.${entityName}QueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
 * ${apiAlias}Controller到Application层的适配
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ${entityName}AdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    ${entityName}QueryContext toQuery(${entityName}Query query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    ${entityName}Context toContext(${entityName}Cmd cmd);
}