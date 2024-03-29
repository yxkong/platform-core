package ${applicationPackage}.executor;

import com.github.platform.core.common.service.BaseExecutor;
import ${domainPackage}.context.${entityName}Context;
import ${domainPackage}.context.${entityName}QueryContext;
import ${domainPackage}.dto.${entityName}Dto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
/**
 * ${apiAlias}执行器接口
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
public interface I${entityName}Executor {
    /**
    * 查询${apiAlias}列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<${entityName}Dto> query(${entityName}QueryContext context);
    /**
    * 新增${apiAlias}
    * @param context 新增上下文
    */
    String insert(${entityName}Context context);
    /**
    * 根据id查询${apiAlias}明细
    * @param id 主键
    * @return 单条记录
    */
    ${entityName}Dto findById(Long id);
    /**
    * 修改${apiAlias}
    * @param context 更新上下文
    */
    void update(${entityName}Context context);
    /**
    * 根据id删除${apiAlias}记录
    * @param id 主键
    */
    void delete(Long id);
}