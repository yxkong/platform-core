package com.github.platform.core.sys.adapter.api.command;
import com.github.platform.core.sys.domain.common.entity.SysCascadeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
/**
 * 级联表增加或修改
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
@Schema(description = "级联表增加或修改")
public class SysCascadeCmd extends SysCascadeBase{
}
