package com.github.platform.core.file.adapter.api.command;

import com.github.platform.core.file.domain.common.entity.SysUploadFileBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* 上传文件表增加或修改
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "上传文件表增加或修改")
public class SysUploadFileCmd  extends SysUploadFileBase{
    @Schema(description = "文件列表")
    private List<FileInfoCmd> files;
}
