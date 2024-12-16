package com.github.platform.core.message.dingtalk.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 钉钉发送消息模板类型
 * @Author: yxkong
 * @Date: 2024/1/21 20:13
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum DingMessageTemplateTypeEnum {
    // 使用 msg_param_map 内容为 "content": "测试消息~"
    text("inner_app_template_text","文本消息模板"),
    // 使用  msg_param_map 内容为："title": "标题", "markdown_content": "# 测试内容"
    markdown("inner_app_template_markdown","markdown模板"),
    // 使用 msg_media_id_param_map  内容为 "img_media_id": "@lAjPDxxx" ，得先上传图片
    photo("inner_app_template_photo","图片消息模板"),
    // 使用  msg_param_map 内容为：
    //        "title": "标题",
    //        "markdown": "# 测试内容",
    //        "btn_orientation": "1",
    //        "btn_title_1": "btn_title_1",
    //        "action_url_1": "dingtalk.com",
    actionCard("inner_app_template_action_card","actionCard模板"),
    ;

    private String type;
    private String  desc;

    /**
     * 判断是否是msg_param_map 参数，只有图片不是，以后添加别的，需要注意
     * @param templateType
     * @return
     */
    public static boolean isMsgParamMap(DingMessageTemplateTypeEnum templateType){
        return !DingMessageTemplateTypeEnum.photo.equals(templateType);
    }
}
