package ${applicationPackage}.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import ${applicationPackage}.executor.I${entityName}Executor;
import ${domainPackage}.context.${entityName}Context;
import ${domainPackage}.context.${entityName}QueryContext;
import ${domainPackage}.dto.${entityName}Dto;
import ${domainPackage}.gateway.I${entityName}Gateway;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Objects;
/**
 * ${apiAlias}执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
@Service
@Slf4j
public class ${entityName}ExecutorImpl extends BaseExecutor implements I${entityName}Executor{
    @Resource
    private I${entityName}Gateway ${lowerEntityName}Gateway;
    /**
    * 查询${apiAlias}列表
    * @param context 查询上下文
    * @return 分页结果
    */
    @Override
    public PageBean<${entityName}Dto> query(${entityName}QueryContext context){
        return ${lowerEntityName}Gateway.query(context);
    };
    /**
    * 新增${apiAlias}
    * @param context 新增上下文
    */
    @Override
    public String insert(${entityName}Context context){
        ${entityName}Dto record = ${lowerEntityName}Gateway.insert(context);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        return record.getStrId();
    }
    /**
    * 根据id查询${apiAlias}明细
    * @param id 主键
    * @return 单条记录
    */
    @Override
    public ${entityName}Dto findById(Long id) {
        return ${lowerEntityName}Gateway.findById(id);
    }
    /**
    * 修改${apiAlias}
    * @param context 更新上下文
    */
    @Override
    public void update(${entityName}Context context) {
        Pair<Boolean, ${entityName}Dto> update = ${lowerEntityName}Gateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    /**
    * 根据id删除${apiAlias}记录
    * @param id 主键
    */
    @Override
    public void delete(Long id) {
        /**此处是为了再gateway上做多条件缓存，如果有必要，先查，后设置值*/
        ${entityName}Context context = ${entityName}Context.builder().id(id).build();
        int d = ${lowerEntityName}Gateway.delete(context);
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
