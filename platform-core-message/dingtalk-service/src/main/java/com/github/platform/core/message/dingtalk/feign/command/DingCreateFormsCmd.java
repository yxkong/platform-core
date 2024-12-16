package com.github.platform.core.message.dingtalk.feign.command;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DingCreateFormsCmd {

    /**
     * 表单模板名称
     */
	private String name;

    /**
     * 表单模板描述
     */
    private String description;

    /**
     * 表单ProcessCode，更新表单模板时需指定ProcessCode
     */
    private String processCode;
    /**表单控件列表，单一表单最大组件个数不超过200*/
    private List<FormComponents> formComponents;
    
    @Data
    @Builder
    public static class FormComponents {
        /**输入框类型  TextField 文本 TextareaField 多行文本*/
        private String componentType;
        private Props props;
    }    
    
    @Data
    @Builder
    public static class Props {
        private String label;
    }

    
}
