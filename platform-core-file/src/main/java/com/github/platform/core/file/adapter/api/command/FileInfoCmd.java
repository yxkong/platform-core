package com.github.platform.core.file.adapter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 *  图片上传
 * @author: yxkong
 * @date: 2024/3/26 09:44
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "文件信息实体")
public class FileInfoCmd implements Serializable {
    @Schema(description = "文件名称")
    private String name;
    @Schema(description = "文件上传进度的百分比，不需要显示进度，直接为0")
    private int percentage;
    @Schema(description = "文件上传的状态：ready（准备就绪，等待上传）、uploading（正在上传）、success（上传成功）、error（上传失败）等")
    private String status;
    @Schema(description = "文件的大小，单位字节")
    private long size;
    @Schema(description = "文件的二进制数据")
    private byte[] raw;
    @Schema(description = "文件的唯一标识符")
    private String uid;
}
