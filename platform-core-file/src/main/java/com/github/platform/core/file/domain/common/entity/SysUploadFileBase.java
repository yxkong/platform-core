package com.github.platform.core.file.domain.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.io.File;

/**
* 上传文件表模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class SysUploadFileBase extends BaseAdminEntity   {
    /** 模块名称 */
    @Schema(description = "模块名称")
    @NotEmpty(message="模块名称（module）不能为空")
    protected String module;
    /** 业务号，业务方关联 */
    @Schema(description = "业务号，业务方关联")
    @NotEmpty(message="业务号，业务方关联（bizNo）不能为空")
    protected String bizNo;
    /** 文件唯一标识 */
    @Schema(description = "文件唯一标识")
    protected String fileId;
    /** 文件路径 */
    @Schema(description = "文件路径")
    protected String filePath;
    /** 文件名 */
    @Schema(description = "文件名")
    protected String fileName;
    /** 文件类型 */
    @Schema(description = "文件类型")
    protected String fileType;
    /** 文件大小，字节 */
    @Schema(description = "文件大小,字节")
    protected Long fileSize;
    /** 存储方式，disk，ctyun（天翼云）,aliyun, */
    @Schema(description = "存储方式，disk，ctyun（天翼云）,aliyun,")
    @NotEmpty(message="存储方式，disk，ctyun（天翼云）,aliyun,（storage）不能为空")
    protected String storage;
    @Schema(description = "删除状态，1已删除，0未删除")
    protected Integer deleted;

    @JsonIgnore
    public String getObjectName(){
        return this.filePath+ File.separator + this.fileId +SymbolConstant.period + this.fileType;
    }
    public boolean isImage(){
        return "jpg".equalsIgnoreCase(this.fileType) || "png".equalsIgnoreCase(this.fileType) || "jpeg".equalsIgnoreCase(this.fileType);
    }
}
