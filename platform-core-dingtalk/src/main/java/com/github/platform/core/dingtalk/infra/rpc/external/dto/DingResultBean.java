package com.github.platform.core.dingtalk.infra.rpc.external.dto;

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
    public boolean isSuc(){
    	return code == 0 ;
    }
    
}
