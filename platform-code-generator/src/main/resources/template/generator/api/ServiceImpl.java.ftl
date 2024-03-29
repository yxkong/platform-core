package ${infraPackage}.service.impl;

import ${domainPackage}.common.entity.${entityName}Base;
import ${mapperPackage}.${entityName}Mapper;
import ${infraPackage}.service.I${entityName}Service;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${apiAlias}服务层，对整个mapper的包装
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
@Service("${lowerEntityName}Service")
public class ${entityName}ServiceImpl implements I${entityName}Service {

    @Resource
    private ${entityName}Mapper ${lowerEntityName}Mapper;

    @Override
    public boolean insert(${entityName}Base record){
        return ${lowerEntityName}Mapper.insert(record) >0;
    }

    @Override
    public boolean updateById(${entityName}Base record){
        return ${lowerEntityName}Mapper.updateById(record)>0;
    }

    @Override
    public ${entityName}Base findById(Long id){
        return ${lowerEntityName}Mapper.findById(id);
    }
    @Override
    public List<${entityName}Base> findByIds(${pkColumnType}[] ids){
        return ${lowerEntityName}Mapper.findByIds(ids);
    }

    @Override
    public List<${entityName}Base> findListBy(${entityName}Base record){
        return  ${lowerEntityName}Mapper.findListBy(record);
    }


}