package ${applicationPackage}.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import ${applicationPackage}.executor.I${entityName}Executor;
import ${domainPackage}.dto.${entityName}Dto;
import ${domainPackage}.gateway.I${entityName}Gateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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
    * 根据id查询${apiAlias}明细
    * @param id 主键
    * @return 单条记录
    */
    @Override
    public ${entityName}Dto findById(Long id) {
        return ${lowerEntityName}Gateway.findById(id);
    }
}
