package com.github.platform.core.sys.adapter.api.command.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 滑块信息
 *
 * @Author: yxkong
 * @Date: 2021/11/15 10:26 AM
 * @version: 1.0
 */
@Data
public class SlidingBlockBase  {

    @NotEmpty(message = "滑块id不能为空")
    @ApiModelProperty(value = "滑块id", required = true)
    String slidingBlockId;
    /**
     * 滑块来源（不同的厂商，yidun,shumei）
     */
    @ApiModelProperty(value = "滑块厂商来源", required = true)
    String slidingBlockSupplier;
}
