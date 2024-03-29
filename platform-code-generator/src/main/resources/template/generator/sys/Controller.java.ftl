package ${adapterPackage}.api.controller;

import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.common.entity.StrIdReq;
import ${adapterPackage}.api.command.${entityName}Cmd;
import ${adapterPackage}.api.command.${entityName}Query;
import ${adapterPackage}.api.convert.${entityName}AdapterConvert;
import ${applicationPackage}.executor.${entityName}Executor;
import ${domainPackage}.context.${entityName}QueryContext;
import ${domainPackage}.dto.${entityName}Dto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * ${apiAlias}
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author ${author}
 * @date ${date}
 * @version 1.0
 */
@RestController
@Tag(name = "${lowerEntityName}",description = "${apiAlias}管理")
@RequestMapping("${urlPrefix}")
@Slf4j
public class ${entityName}Controller extends BaseController{
    @Resource
    private ${entityName}Executor ${lowerEntityName}executor;
    @Resource
    private ${entityName}AdapterConvert ${lowerEntityName}Convert;

    /**
    * 查询${apiAlias}列表
    * @param query 查询实体
    * @return 分页结果
    */
    @OptLog(module="${lowerEntityName}",title="查询${apiAlias}列表",persistent = false)
    @Operation(summary = "查询${apiAlias}列表",tags = {"${lowerEntityName}"})
    @PostMapping("/query")
    public ResultBean<PageBean<${entityName}Dto>> query(@RequestBody ${entityName}Query query){
        ${entityName}QueryContext context = ${lowerEntityName}Convert.toQuery(query);
        PageBean<${entityName}Dto> pageBean = ${lowerEntityName}executor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增${apiAlias}
    * @param cmd 新增实体
    * @return 操作结果
    */
    @OptLog(module="${lowerEntityName}",title="新增${apiAlias}",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增${apiAlias}",tags = {"${lowerEntityName}"})
    @PostMapping("/add")
    public ResultBean<String> add(@Validated @RequestBody ${entityName}Cmd cmd) {
        String id = ${lowerEntityName}executor.insert(${lowerEntityName}Convert.toContext(cmd));
        return buildSucResp(id);
    }

    /**
    * 根据id查询${apiAlias}明细
    * @param id 主键id
    * @return 单条记录
    */
    @OptLog(module="${lowerEntityName}",title="根据id查询${apiAlias}明细",optType = LogOptTypeEnum.detail,persistent = false)
    @Operation(summary = "根据id查询${apiAlias}明细",tags = {"${lowerEntityName}"})
    @PostMapping("/detail")
    public ResultBean<${entityName}Dto> detail(@Validated @RequestBody StrIdReq id) {
        ${entityName}Dto dto = ${lowerEntityName}executor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除${apiAlias}记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="${lowerEntityName}",title="根据id删除${apiAlias}记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除${apiAlias}记录",tags = {"${lowerEntityName}"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        ${lowerEntityName}executor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改${apiAlias}
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="${lowerEntityName}",title="修改${apiAlias}",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改${apiAlias}",tags = {"${lowerEntityName}"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody ${entityName}Cmd cmd) {
        ${lowerEntityName}executor.update(${lowerEntityName}Convert.toContext(cmd));
        return buildSucResp();
    }
}