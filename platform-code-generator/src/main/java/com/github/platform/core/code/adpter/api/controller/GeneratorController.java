package com.github.platform.core.code.adpter.api.controller;

import com.github.platform.core.code.adpter.api.command.CodeViewCmd;
import com.github.platform.core.code.adpter.api.command.GenCmd;
import com.github.platform.core.code.adpter.api.command.SyncCommand;
import com.github.platform.core.code.adpter.api.command.TableQuery;
import com.github.platform.core.code.adpter.api.convert.GeneratorConvert;
import com.github.platform.core.code.application.executor.IGeneratorExecutor;
import com.github.platform.core.code.domain.context.GenContext;
import com.github.platform.core.code.domain.context.TableQueryContext;
import com.github.platform.core.code.domain.dto.GenConfigDto;
import com.github.platform.core.code.domain.dto.GenDto;
import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: yxkong
 * @date: 2023/4/25 12:11 PM
 * @version: 1.0
 */
@Tag(name = "gen",description = "代码生成")
@RestController
@RequestMapping("/sys/tool/gen")
@Slf4j
public class GeneratorController extends BaseController {

    @Resource
    private IGeneratorExecutor IGeneratorExecutor;
    @Resource
    private GeneratorConvert convert;
    @Operation(summary = "同步表，为空同步全量",tags = {"gen"})
    @OptLog(module = "generator",title = "同步表")
    @PostMapping("/sync")
    public ResultBean sync(@RequestBody SyncCommand sync){
        IGeneratorExecutor.sync(sync.getDbName(),sync.getTableName());
        return buildSucResp();
    }

    @Operation(summary = "查询genConfig中对应的表",tags = {"gen"})
    @PostMapping("query")
    public ResultBean<PageBean<GenConfigDto>> query(@RequestBody TableQuery tableQuery){
        TableQueryContext context =  convert.toQuery(tableQuery);
        PageBean<GenConfigDto> pageBean = IGeneratorExecutor.query(context);
        return buildSucResp(pageBean);
    }
    @Operation(summary = "根据id查询表的代码生成配置",tags = {"gen"})
    @PostMapping("config")
    public ResultBean<GenDto> config(@Validated @RequestBody StrIdReq id){
        GenDto dto = IGeneratorExecutor.findById(id.getId());
        return buildSucResp(dto);
    }
    @Operation(summary = "代码生成预览",tags = {"gen"})
    @OptLog(module = "generator",title = "preview",persistent = false)
    @PostMapping("preview")
    public ResultBean<List<Map<String, Object>>> preview(@RequestBody CodeViewCmd cmd) throws Exception {
        if (Objects.isNull(cmd) || Objects.isNull(cmd.getTableName())){
            return ResultBeanUtil.fail("tableName不能为空",null);
        }
        List<Map<String, Object>> preview = IGeneratorExecutor.preview(cmd.getDbName(),cmd.getTableName(),cmd.getCodeType());
        if (preview == null){
            return ResultBeanUtil.fail("请检查配置必填项！",null);
        }
        return buildSucResp(preview);
    }
    @Operation(summary = "修改代码生成配置",tags = {"gen"})
    @OptLog(module = "generator",title = "modify")
    @PostMapping("modify")
    public ResultBean modify(@Validated  @RequestBody GenCmd cmd){
        GenContext genConfigContext = convert.toContext(cmd);
        IGeneratorExecutor.update(genConfigContext);
        return buildSucResp();
    }

    @Operation(summary = "修改代码生成配置",tags = {"gen"})
    @OptLog(module = "generator",title = "modify")
    @PostMapping("delete")
    public ResultBean delete(@Validated  @RequestBody StrIdReq id){
        IGeneratorExecutor.delete(id.getId());
        return buildSucResp();
    }

    @OptLog(module = "generator",title = "download")
    @Operation(summary = "代码下载",tags = {"gen"})
    @PostMapping("/download")
    public void download(HttpServletResponse response,@RequestBody CodeViewCmd cmd) throws Exception {
        byte[] data = IGeneratorExecutor.downloadCode(cmd.getDbName(),cmd.getTableName(),cmd.getCodeType());
        genCode(response, data);
    }
    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        String encodedTableName = URLEncoder.encode(tableName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"yxkong-platform.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        try {
            out.write(data);
        } catch (IOException e) {
            log.error("代码生成异常",e);
        } finally {
            if (null != out){
                out.close();
            }
        }
    }

}
