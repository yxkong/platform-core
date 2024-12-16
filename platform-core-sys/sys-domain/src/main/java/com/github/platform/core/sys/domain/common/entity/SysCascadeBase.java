package com.github.platform.core.sys.domain.common.entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.github.platform.core.common.entity.BaseAdminEntity;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * 级联表模型实体
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-01 20:57:44.667
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class SysCascadeBase extends BaseAdminEntity   {
    /** 父级id */
    @Schema(description = "父级id")
    protected Long parentId;
    /** 祖级列表 */
    @Schema(description = "祖级列表")
    protected String ancestors;
    /** 编码 */
    @Schema(description = "编码")
    protected String code;
    /** 名称 */
    @Schema(description = "名称")
    protected String name;
    /** 当前层级顺序 */
    @Schema(description = "当前层级顺序")
    protected Integer sort;
    /** 是否叶子节点 */
    @Schema(description = "是否叶子节点,1是，0否")
    protected Integer leaf;

}
