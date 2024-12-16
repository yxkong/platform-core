package com.github.platform.core.sys.domain.common.query;
import com.github.platform.core.common.entity.query.PageQueryBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 级联表查询基类
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-01 20:57:44.667
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "级联表查询")
public class SysCascadeQueryBase extends PageQueryBaseEntity {
    /** 父级id */
    @Schema(description = "父级id")
    private Long parentId;
    /** 祖级列表 */
    @Schema(description = "祖级列表")
    private String ancestors;
    /** 编码 */
    @Schema(description = "编码")
    private String code;
    /** 名称 */
    @Schema(description = "名称")
    private String name;
}