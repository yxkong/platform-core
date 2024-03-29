package ${infraPackage}.service;

import com.github.pagehelper.PageInfo;
import ${domainPackage}.common.entity.${entityName}Base;
import java.util.List;
import java.util.Map;
/**
 * ${apiAlias}服务层，对整个mapper的包装
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
public interface I${entityName}Service  {
    /**
    * 插入实体
    * @param record 实体
    * @return 返回插入结果
    */
    boolean insert(${entityName}Base record);
    /**
    * 通过主键id 更新实体
    * @param record 实体
    * @return 1成功  其它失败
    */
    boolean updateById(${entityName}Base record);
    /**
    * 通过主键id 获取实体对象
    * @param id 主键
    * @return 返回单条
    */
    ${entityName}Base findById(${pkColumnType} id);
    /**
    * 通过主键ids 获取多个实体对象(最多200条)
    * @param ids 主键
    * @return 返回列表
    */
    List<${entityName}Base> findByIds(${pkColumnType}[] ids);
    /**
    * 通过map参数获取列表
    * @param params 查询参数
    * @return List<${entityName}Base>
    */
    List<${entityName}Base> findList(Map<String,Object> params);
    /**
    * 通过map参数获取列表
    * @param record 查询参数
    * @return List<${entityName}Base>
    */
    List<${entityName}Base> findListBy(${entityName}Base record);
    /**
    * 通过map参数获取列表 分页
    * @param params 查询参数
    * @param pageNum 页数
    * @param pageSize 每页大小
    * @return PageInfo<${entityName}Base> 分页结果
    */
    PageInfo<${entityName}Base> findPageInfo(Map<String,Object> params,int pageNum,int pageSize);
    /**
    * 通过实体参数获取列表分页
    * @param record 查询实体
    * @param pageNum 页数
    * @param pageSize 每页大小
    * @return PageInfo<${entityName}Base> 分页结果
    */
    PageInfo<${entityName}Base> findPageInfo(${entityName}Base record,int pageNum,int pageSize);
    /**
    * 通过map参数获取 总数
    * @param params 查询参数
    * @return 总数
    */
    long findListCount(Map<String,Object> params);
    /**
    * 通过实体参数获取 总数
    * @param record 查询实体
    * @return 总数
    */
    long findListByCount(${entityName}Base record);
    /**
    * 通过主键id 删除
    * @param id 主键
    * @return 返回删除结果
    */
    boolean deleteById(${pkColumnType} id);
    /**
    * 批量删除
    * @param ids 主键
    * @return 删除条数
    */
    int deleteByIds(${pkColumnType}[] ids);
}