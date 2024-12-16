package com.github.platform.core.sys.domain.context;
import com.github.platform.core.sys.domain.common.entity.SysCascadeBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
 * 级联表增加或修改上下文
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
public class SysCascadeContext extends SysCascadeBase {
}
