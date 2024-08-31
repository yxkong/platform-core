package com.github.platform.core.file.adapter.api.controller;

import com.github.platform.core.common.entity.StrIdReq;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.file.adapter.api.command.FileInfoCmd;
import com.github.platform.core.file.adapter.api.command.SysUploadFileCmd;
import com.github.platform.core.file.adapter.api.command.SysUploadFileQuery;
import com.github.platform.core.file.adapter.api.convert.SysUploadFileAdapterConvert;
import com.github.platform.core.file.application.executor.ISysUploadFileExecutor;
import com.github.platform.core.file.application.executor.IUploadFileExecutor;
import com.github.platform.core.file.domain.context.SysUploadFileContext;
import com.github.platform.core.file.domain.context.SysUploadFileQueryContext;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.file.domain.entity.UploadEntity;
import com.github.platform.core.log.domain.constants.LogOptTypeEnum;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
* 上传文件表
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
@RestController
@Tag(name = "uploadFile",description = "上传文件表管理")
@RequestMapping("sys/file/upload")
@Slf4j
public class SysUploadFileController extends BaseController{
    @Resource
    private ISysUploadFileExecutor executor;
    @Resource
    private IUploadFileExecutor uploadFileExecutor;
    @Resource
    private SysUploadFileAdapterConvert convert;

    /**
    * 查询上传文件表列表
    * @param query 查询实体
    * @return 分页结果
    */
    @Operation(summary = "查询上传文件表列表",tags = {"uploadFile"})
    @PostMapping("/query")
    public ResultBean<PageBean<SysUploadFileDto>> query(@RequestBody SysUploadFileQuery query){
        SysUploadFileQueryContext context = convert.toQuery(query);
        PageBean<SysUploadFileDto> pageBean = executor.query(context);
        return buildSucResp(pageBean);
    }

    /**
    * 新增上传文件表
    * @param files 文件
     *     可用对象替代字段       @ModelAttribute SysUploadFileCmd
    * @return 操作结果
    */
    @OptLog(module="uploadFile",title="新增上传文件表",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增上传文件表",tags = {"sysUploadFile"})
    @PostMapping("/add")
    public ResultBean<List<UploadEntity>> add(@RequestParam("files") List<MultipartFile> files, @RequestParam("module") String module,
                          @RequestParam("bizNo") String bizNo) {
        if (CollectionUtil.isEmpty(files)){
            throw exception(ResultStatusEnum.PARAM_EMPTY);
        }
        List<UploadEntity> rst = new ArrayList<>();
        InputStream fileInputStream = null;
        for (MultipartFile file:files){
            try {
                fileInputStream = file.getInputStream();
            } catch (IOException e) {
                throw exception(ResultStatusEnum.COMMON_UPLOAD_STREAM_EXCEPTION);
            }
            UploadEntity uploadEntity = uploadFileExecutor.uploadAndSave(module, bizNo, null, file.getOriginalFilename(), file.getSize(), fileInputStream);
            rst.add(uploadEntity);
        }

        return buildSucResp(rst);
    }
    /**
     * 文件上传
     * @param cmd 文件
     *     可用对象替代字段       @ModelAttribute SysUploadFileCmd
     * @return 操作结果
     */
    @OptLog(module="uploadFile",title="新增上传文件表",optType = LogOptTypeEnum.add)
    @Operation(summary = "新增上传文件表",tags = {"sysUploadFile"})
    @PostMapping("/upload")
    public ResultBean<List<UploadEntity>> upload(@RequestBody SysUploadFileCmd cmd) {
        if (CollectionUtil.isEmpty(cmd.getFiles())){
            exception(ResultStatusEnum.PARAM_EMPTY);
        }
        List<UploadEntity> rst = new ArrayList<>();
        InputStream fileInputStream = null;
        for (FileInfoCmd fileInfo:cmd.getFiles()){
            // 创建 ByteArrayInputStream 以将 byte[] 转换为 InputStream
            fileInputStream = new ByteArrayInputStream(fileInfo.getRaw());
            UploadEntity uploadEntity = uploadFileExecutor.uploadAndSave(cmd.getModule(), cmd.getBizNo(), null, fileInfo.getName(), fileInfo.getSize(), fileInputStream);
            rst.add(uploadEntity);
        }

        return buildSucResp(rst);
    }

    /**
    * 根据id查询上传文件表明细
    * @param id 主键id
    * @return 单条记录
    */
    @Operation(summary = "根据id查询上传文件表明细",tags = {"uploadFile"})
    @PostMapping("/detail")
    public ResultBean<SysUploadFileDto> detail(@Validated @RequestBody StrIdReq id) {
        SysUploadFileDto dto = executor.findById(id.getId());
        return buildSucResp(dto);
    }

    /**
     * 根据id删除上传文件表记录
     * @param id 主键id
     * @return 操作结果
     */
    @OptLog(module="uploadFile",title="根据id删除上传文件表记录",optType = LogOptTypeEnum.delete)
    @Operation(summary = "根据id删除上传文件表记录",tags = {"uploadFile"})
    @PostMapping("/delete")
    public ResultBean delete(@Validated @RequestBody StrIdReq id) {
        executor.delete(id.getId());
        return buildSucResp();
    }

    /**
     * 修改上传文件表
     * @param cmd 修改实体
     * @return 操作结果
     */
    @OptLog(module="uploadFile",title="修改上传文件表",optType = LogOptTypeEnum.modify)
    @Operation(summary = "修改上传文件表",tags = {"uploadFile"})
    @PostMapping("/modify")
    public ResultBean modify(@Validated @RequestBody SysUploadFileCmd cmd) {
        SysUploadFileContext context = convert.toContext(cmd);
        executor.update(context);
        return buildSucResp();
    }
}