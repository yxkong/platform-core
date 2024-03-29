package com.github.platform.core.sys.adapter.api.command.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 下拉框查询关键词
 * @author: yxkong
 * @date: 2023/11/27 17:51
 * @version: 1.0
 */
@Schema(name = "KeyQuery",title = "key查询")
@Data
@NoArgsConstructor
public class KeyQuery implements Serializable {
    /**
     * 关键词
     */
    @NotEmpty(message = "关键词(key)不能为空")
    @Schema(description ="关键词")
    private String key;

}
