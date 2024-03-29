package ${infraPackage}.gateway.impl;

import ${domainPackage}.common.entity.${entityName}Base;
import ${domainPackage}.context.${entityName}Context;
import ${domainPackage}.dto.${entityName}Dto;
import ${domainPackage}.gateway.I${entityName}Gateway;
import ${infraPackage}.convert.${entityName}InfraConvert;
import ${mapperPackage}.${entityName}Mapper;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
/**
 * ${apiAlias}网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
@Service
public class ${entityName}GatewayImpl extends BaseGatewayImpl implements I${entityName}Gateway {

    @Resource
    private ${entityName}Mapper ${lowerEntityName}Mapper;
    @Resource
    private ${entityName}InfraConvert ${lowerEntityName}Convert;

    @Override
    public ${entityName}Dto insert(${entityName}Context context) {
        ${entityName}Base ${lowerEntityName}Base = ${lowerEntityName}Convert.to${entityName}Base(context);
        ${lowerEntityName}Mapper.insert(${lowerEntityName}Base);
        return ${lowerEntityName}Convert.toDto(${lowerEntityName}Base);
    }

    @Override
    public ${entityName}Dto findById(Long id) {
        ${entityName}Base record = ${lowerEntityName}Mapper.findById(id);
        return ${lowerEntityName}Convert.toDto(record);
    }

    @Override
    public Boolean update(${entityName}Context context){
        ${entityName}Base record = ${lowerEntityName}Convert.to${entityName}Base(context);
        return ${lowerEntityName}Mapper.updateById(record) > 0;
    }

    @Override
    public List<${entityName}Dto> findListBy(${entityName}Context context){
        ${entityName}Base record = ${lowerEntityName}Convert.to${entityName}Base(context);
        List<${entityName}Base> list = ${lowerEntityName}Mapper.findListBy(record);
        return ${lowerEntityName}Convert.toDtos(list);
    }
}
