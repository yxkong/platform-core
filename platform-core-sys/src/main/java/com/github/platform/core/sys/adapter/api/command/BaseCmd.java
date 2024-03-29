package com.github.platform.core.sys.adapter.api.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author: yxkong
 * @date: 2022/4/26 2:16 PM
 * @version: 1.0
 */
@Data
public class BaseCmd implements Serializable {
    @Schema(description ="用户token", required = false)
    String token;
    @Schema(description ="请求时间戳")
    long t;
}
