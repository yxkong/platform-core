package com.github.platform.core.file.domain.entity;

import com.github.platform.core.standard.annotation.DomainEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yxkong
 * @date: 2022/8/3 4:42 PM
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "文件上传信息")
@DomainEntity
public class UploadEntity {
    @Schema(description = "存储方式")
    private String storage;
    @Schema(description = "文件id")
    private String fileId;
    @Schema(description = "文件url")
    private String url;

    private String thumbUrl;
}
