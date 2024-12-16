package com.github.platform.core.message.dingtalk.feign.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 钉钉工作通知
 *
 * @author: yxkong
 * @date: 2024/4/18 22:16
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DingWorkNoticeCmd {
    /**发送消息时使用的微应用的AgentID*/
    @JsonProperty("agent_id")
    private Long agentId ;
    /**接收的部门id，以英文逗号分隔*/
    @JsonProperty("dept_id_list")
    private String deptIdList ;
    /**接收的用户id，以英文逗号分隔*/
    @JsonProperty("userid_list")
    private String userIdList ;
    @JsonIgnore
    /**消息类型*/
    private String msgType;
    /**消息内容，最长不超过2048个字节*/
    private Map<String,Object> msg;

    /**
     * 添加文本消息
     * @param text
     */
    public void addText(String text){
        msg = new LinkedHashMap<>();
        msg.put("msgtype","text");
        Map<String,String> textMap = new HashMap<>();
        textMap.put("content",text);
        msg.put("text",textMap);
    }
    /**
     * 添加媒体消息
     * @param mediaId  调用上传媒体文件返回的id
     */
    public void addImage(String mediaId){
        msg = new LinkedHashMap<>();
        msg.put("msgtype","image");
        Map<String,String> imageMap = new HashMap<>();
        imageMap.put("media_id",mediaId);
        msg.put("image",imageMap);
    }
    /**
     * 添加卡片消息
     * @param title  透出到会话列表和通知的文案
     * @param  markdown  markdown 类型的内容
     * @param singleTitle 使用整体跳转ActionCard样式时的标题。必须与single_url同时设置，最长20个字符
     * @param singleUrl 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符
     */
    public void addActionCard(String title,String markdown,String singleTitle,String singleUrl){
        msg = new LinkedHashMap<>();
        msg.put("msgtype","action_card");
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("markdown",markdown);
        map.put("single_title",singleTitle);
        map.put("single_url",singleUrl);
        msg.put("action_card",map);
    }

    /**
     * 添加markdown消息
     * @param title 消息标题
     * @param text 消息内容
     */
    public void addMarkdown(String title,String text){
        msg = new LinkedHashMap<>();
        msg.put("msgtype","markdown");
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        map.put("text",text);
        msg.put("markdown",map);
    }
}
