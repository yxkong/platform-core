package ${applicationPackage}.executor;

import ${domainPackage}.dto.${entityName}Dto;
/**
 * ${apiAlias}执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
public interface I${entityName}Executor{
    /**
    * 根据id查询${apiAlias}明细
    * @param id 主键
    * @return 单条记录
    */
    public ${entityName}Dto findById(Long id);
}
