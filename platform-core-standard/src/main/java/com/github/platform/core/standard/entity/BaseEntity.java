package com.github.platform.core.standard.entity;

import com.github.platform.core.standard.validate.Modify;
import com.github.platform.core.standard.validate.Rest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公共属性
 * 加上 @JsonIgnore 序列化的时候spring框架自带的jackson 会把映射排除，需要前端传递的，不要添加@JsonIgnore
 * @author: yxkong
 * @date: 2023/7/26 4:06 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class BaseEntity implements Serializable {
    /** 主键 */
    @NotNull(message="主键（id）不能为空",groups = {Modify.class, Rest.class})
    @Schema(description = "主键")
    protected Long id;
    @Schema(description = "加密id")
    protected String strId;
    @Schema(description = "租户")
    protected Integer tenantId;
    /**创建时间*/
    @Schema(description = "创建时间")
    protected LocalDateTime createTime;
    /**更新时间*/
    @Schema(description = "更新时间")
    protected LocalDateTime updateTime;
    /**备注*/
    @Schema(description = "备注")
    protected String remark;

}
