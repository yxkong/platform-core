package com.github.platform.core.standard.entity.context;

import com.github.platform.core.standard.constant.MessageNoticeChannelTypeEnum;
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
    /**通道类型*/
    private MessageNoticeChannelTypeEnum channelType;
    /**事件类型*/
    private String eventType;
    /**模板编号*/
    private String tempNo;
    /**消息标题*/
    private String title;
    /**消息唯一id*/
    private String msgId;
    /**租户id*/
    private Integer tenantId;
    /**接收人*/
    @Builder.Default
    private List<String> recipient = new ArrayList<>();
    /**群id*/
    private String groupId;
    /**发送时间*/
    @Builder.Default
    private LocalDateTime sendTime = LocalDateTimeUtil.dateTime();
    /**操作用户*/
    private String optUser;

    /**元数据*/
    @Builder.Default
    private Map<String,Object> metas = new HashMap<>();

    /**
     * 添加元数据
     * @param key 参数key
     * @param val 参数值
     */
    public void addMeta(String key,Object val){
        if (Objects.isNull(this.metas)){
            this.metas = new HashMap<>();
        }
        this.metas.put(key,val);
    }
    public void addRecipient(String loginName){
        if (recipient == null) {
            recipient = new ArrayList<>();
        }
        this.recipient.add(loginName);
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
}
