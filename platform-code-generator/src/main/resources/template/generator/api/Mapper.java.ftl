package ${mapperPackage};
import ${domainPackage}.common.entity.${entityName}Base;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * ${apiAlias} mapper
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
public interface ${entityName}Mapper  {

    /**
    * 插入实体
    * @param record 实体
    * @return 插入结果，1成功，0失败
    */
    int insert(${entityName}Base record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    int updateById(${entityName}Base record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回结果
    */
    ${entityName}Base findById(${pkColumnType} id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键id
    * @return 返回列表
    */
    List<${entityName}Base> findByIds(@Param("ids") ${pkColumnType}[] ids);
    /**
    * 通过实体查询
    * @param record 参数实体
    * @return List<${entityName}Base>
    */
    List<${entityName}Base> findListBy(${entityName}Base record);

}