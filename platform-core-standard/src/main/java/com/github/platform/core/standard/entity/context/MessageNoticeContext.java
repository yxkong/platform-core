package com.github.platform.core.standard.entity.context;

import com.github.platform.core.standard.constant.NoticeTypeEnum;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 消息通知上下文
 * @Author: yxkong
 * @Date: 2024/12/5
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageNoticeContext  implements Serializable {
    /**事件类型*/
    private String eventType;
    /**模板编号*/
    private String tempNo;
    /**消息标题*/
    private String title;
    /**接收人*/
    @Builder.Default
    private List<String> recipient = new ArrayList<>();
    @Builder.Default
    private NoticeChannelInfo noticeChannelInfo = new NoticeChannelInfo();

    /**发送时间*/
    @Builder.Default
    private LocalDateTime sendTime = LocalDateTimeUtil.dateTime();

    /**元数据*/
    @Builder.Default
    private Map<String,Object> metas = new HashMap<>();

    public MessageNoticeContext addNoticeChannel(String groupId,String channelType){
        this.noticeChannelInfo.groupId = groupId;
        this.noticeChannelInfo.channelType = channelType;
        return this;
    }
    /**
     * 添加元数据
     * @param key 参数key
     * @param val 参数值
     */
    public MessageNoticeContext addMeta(String key,Object val){
        if (Objects.isNull(this.metas)){
            this.metas = new HashMap<>();
        }
        this.metas.put(key,val);
        return this;
    }
    public MessageNoticeContext addRecipient(String loginName){
        if (recipient == null) {
            recipient = new ArrayList<>();
        }
        this.recipient.add(loginName);
        return this;
    }


    public List<String> getRecipient() {
        if (recipient == null) {
            recipient = new ArrayList<>();
        }
        return recipient;
    }

    public Map<String, Object> getMetas() {
        if (metas == null) {
            metas = new HashMap<>();
        }
        return metas;
    }
    @Data
    public static class NoticeChannelInfo{
        /**群id*/
        private String groupId;
        /**通道类型*/
        private String channelType;
        public String getNoticeType(){
            if (Objects.isNull(this.groupId) || "".equalsIgnoreCase(this.groupId)){
                return NoticeTypeEnum.WORK.getType();
            }
            return NoticeTypeEnum.GROUP.getType();
        }
    }
}
