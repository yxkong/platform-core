package com.github.platform.core.workflow.domain.context;

import com.github.platform.core.workflow.domain.common.opt.ProcessRunBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 工作流启动上线问
 * @author: yxkong
 * @date: 2023/10/7 4:27 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessRunContext extends ProcessRunBase {


}
