package com.github.platform.core.standard.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * api接口返回数据包装体
 *
 * @author: yxkong
 * @date: 2021/4/5 8:28 下午
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResultBean<T> implements Serializable {
    public static final String STATUS ="status" ;
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String DATA = "data";
    private static final long serialVersionUID = -7602280271453240278L;
    /**
     * 返回状态,1为成功,其它均为失败
     */
    @Schema(description = "返回状态,1为成功,其它均为失败")
    private String status;
    /**
     * 返回状态对应的描述
     */
    @Schema(description = "返回状态对应的描述")
    private String message;

    /**
     * 返回数据体
     */
    @Schema(description = "返回数据体")
    private T data;

    /**
     * 请求返回时间
     */
    @Builder.Default
    @Schema(description = "请求返回时间")
    private Long timestamp = System.currentTimeMillis();

    /**
     * 格式化message
     *
     * @param args
     */
    @JsonIgnore
    public ResultBean format(Object... args) {
        this.message = String.format(message, args);
        return this;
    }

    @JsonIgnore
    public boolean isSucc() {
        return ResultStatusEnum.SUCCESS.getStatus().equals(this.status);
    }

    /**
     * 系统异常
     * @return
     */
    @JsonIgnore
    public boolean isError(){
        return  ResultStatusEnum.ERROR.getStatus().equals(this.status);
    }

    /**
     * 业务异常
     * @return
     */
    @JsonIgnore
    public boolean isBizFail(){
        return !isSucc() && !isError();
    }

    /**
     * 业务失败或者 返回数据为null
     * @return
     */
    @JsonIgnore
    public boolean isFailOrDataNull(){
        return !isSucc() || Objects.isNull(this.data);
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
