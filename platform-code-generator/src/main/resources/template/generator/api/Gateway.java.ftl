package ${domainPackage}.gateway;

import ${domainPackage}.context.${entityName}Context;
import ${domainPackage}.dto.${entityName}Dto;
import org.apache.commons.lang3.tuple.Pair;
import ${domainPackage}.common.entity.${entityName}Base;
import java.util.List;

/**
 * ${apiAlias}网关层，隔离模型和实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
public interface I${entityName}Gateway {
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
    Boolean update(${entityName}Context context);
    /**
    * 通过实体参数获取列表
    * @param context 查询参数
    * @return List<${entityName}Dto>
    */
    List<${entityName}Dto> findListBy(${entityName}Context context);
}
