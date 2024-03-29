package ${infraPackage}.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${domainPackage}.common.entity.${entityName}Base;
import ${domainPackage}.context.${entityName}Context;
import ${domainPackage}.context.${entityName}QueryContext;
import ${domainPackage}.dto.${entityName}Dto;
import ${domainPackage}.gateway.I${entityName}Gateway;
import ${mapperPackage}.${entityName}Mapper;
import ${infraPackage}.convert.${entityName}InfraConvert;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
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
    public PageBean<${entityName}Dto> query(${entityName}QueryContext context) {
        ${entityName}Base record = ${lowerEntityName}Convert.to${entityName}Base(context);
        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<${entityName}Base> list = ${lowerEntityName}Mapper.findListBy(record);
        return ${lowerEntityName}Convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public ${entityName}Dto insert(${entityName}Context context) {
        ${entityName}Base record = ${lowerEntityName}Convert.to${entityName}Base(context);
        ${lowerEntityName}Mapper.insert(record);
        return ${lowerEntityName}Convert.toDto(${lowerEntityName}Base);
    }

    @Override
    public ${entityName}Dto findById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        ${entityName}Base ${lowerEntityName}Base = ${lowerEntityName}Mapper.findById(id);
        return ${lowerEntityName}Convert.toDto(${lowerEntityName}Base);
    }

    @Override
    public Pair<Boolean, ${entityName}Dto> update(${entityName}Context context) {
        ${entityName}Base record = ${lowerEntityName}Convert.to${entityName}Base(context);
        int flag = ${lowerEntityName}Mapper.updateById(record);
        return Pair.of( flag>0 , ${lowerEntityName}Convert.toDto(${lowerEntityName}Base)) ;
    }

    @Override
    public int delete(${entityName}Context context) {
        return ${lowerEntityName}Mapper.deleteById(context.getId());
    }
}
