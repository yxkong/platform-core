package ${domainPackage}.gateway;

import ${domainPackage}.context.${entityName}Context;
import ${domainPackage}.context.${entityName}QueryContext;
import ${domainPackage}.dto.${entityName}Dto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

/**
 * ${apiAlias}网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
public interface I${entityName}Gateway {
    /**
    * 查询${apiAlias}列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<${entityName}Dto> query(${entityName}QueryContext context);
    /**
    * 新增${apiAlias}
    * @param context 新增上下文
    * @return 返回结果
    */
    ${entityName}Dto insert(${entityName}Context context);
    /**
    * 根据id查询${apiAlias}明细
    * @param id 主键
    * @return 结果
    */
    ${entityName}Dto findById(Long id);
    /**
    * 修改${apiAlias}
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, ${entityName}Dto> update(${entityName}Context context);
    /**
    * 根据id删除${apiAlias}记录
    * @param context 删除上下文（由id改为上下文了，是为了兼容cache的多处理）
    * @return 删除结果
    */
    int delete(${entityName}Context context);
}
