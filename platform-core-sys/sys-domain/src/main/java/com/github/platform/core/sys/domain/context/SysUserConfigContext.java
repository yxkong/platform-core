package com.github.platform.core.sys.domain.context;
import com.github.platform.core.sys.domain.common.entity.SysUserConfigBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
 * 用户配置增加或修改上下文
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUserConfigContext extends SysUserConfigBase {
}
