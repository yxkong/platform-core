package ${infraPackage}.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${domainPackage}.common.entity.${entityName}Base;
import ${mapperPackage}.${entityName}Mapper;
import ${infraPackage}.service.I${entityName}Service;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<${entityName}Base> findList(Map<String,Object> params){
        return  ${lowerEntityName}Mapper.findList(params);
    }
    @Override
    public List<${entityName}Base> findListBy(${entityName}Base record){
        return  ${lowerEntityName}Mapper.findListBy(record);
    }

    @Override
    public PageInfo<${entityName}Base> findPageInfo(Map<String,Object> params, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<${entityName}Base> list = ${lowerEntityName}Mapper.findList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<${entityName}Base> findPageInfo(${entityName}Base record,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<${entityName}Base> list = ${lowerEntityName}Mapper.findListBy(record);
        return new PageInfo<>(list);
    }

    @Override
    public long findListCount(Map<String,Object> params){
        return ${lowerEntityName}Mapper.findListCount(params);
    }

    @Override
    public long findListByCount(${entityName}Base record){
        return ${lowerEntityName}Mapper.findListByCount(record);
    }

    @Override
    public boolean deleteById(Long id){
        return	${lowerEntityName}Mapper.deleteById(id)>0;
    }

    @Override
    public int deleteByIds(Long[] ids){
        return	${lowerEntityName}Mapper.deleteByIds(ids);
    }
}