package com.github.platform.core.file.domain.dto;

import com.github.platform.core.file.domain.common.entity.SysUploadFileBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
/**
* 上传文件表传输实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "上传文件表传输实体")
public class SysUploadFileDto extends SysUploadFileBase{
    /**url*/
    private String url;
    /**缩略图*/
    private String thumbUrl;
    /*是否永久链接*/
    private Boolean permanent;
}