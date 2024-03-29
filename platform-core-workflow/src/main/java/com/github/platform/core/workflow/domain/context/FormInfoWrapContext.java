package com.github.platform.core.workflow.domain.context;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 表单添加上下文
 * @author: yxkong
 * @date: 2023/11/27 17:36
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class FormInfoWrapContext {

    FormContext basic;
    List<FormInfoContext> infos;
}
