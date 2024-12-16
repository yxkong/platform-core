package com.github.platform.core.dingtalk.domain.context;
import com.github.platform.core.dingtalk.domain.common.entity.DingDeptBase;
import lombok.experimental.SuperBuilder;
import lombok.*;
/**
 * 钉钉部门增加或修改上下文
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-01-18 15:49:32.694
 * @version 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DingDeptContext extends DingDeptBase {
}
