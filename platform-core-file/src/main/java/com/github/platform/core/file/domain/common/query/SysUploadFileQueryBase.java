package com.github.platform.core.file.domain.common.query;

import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
* 上传文件表查询基类
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Schema(description = "上传文件表查询")
public class SysUploadFileQueryBase extends PageQueryBaseEntity {
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
    @NotEmpty(message="文件唯一标识（fileId）不能为空")
    protected String fileId;
    /** 文件名 */
    @Schema(description = "文件名")
    protected String fileName;
    /** 存储方式，disk，ctyun（天翼云）,aliyun, */
    @Schema(description = "存储方式，disk，ctyun（天翼云）,aliyun,")
    @NotEmpty(message="存储方式，disk，ctyun（天翼云）,aliyun,（storage）不能为空")
    protected String storage;
}