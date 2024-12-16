package com.github.platform.core.message.dingtalk.feign.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 钉钉resutl基础类
 * @author
 * @param <T>
 */
@Data
public class DingResultBean<T> {
    /**请求是否成功，true表示执行成功，否则表示失败。*/
    @JsonAlias({"success","succ"})
    private boolean success;
    @JsonProperty("request_id")
    private String requestId;
    /**返回码*/
    @JsonProperty("errcode")
	private int code;
    /**返回码描述*/
    @JsonProperty("errmsg")
    private String msg;
    private T result;

    /**
     * 这里做了兼容
     * 有的是code 为0为成功，还有的是success 为true
     * @return
     */
    public boolean isSuc(){
    	return code == 0 || this.success ;
    }
    
}
