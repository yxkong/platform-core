package com.github.platform.core.standard.entity.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 分页查询通用VO
 * @author: yxkong
 * @date: 2023/3/13 11:05 AM
 * @version: 1.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class PageQueryReqBase implements Serializable {
    @Schema(description = "租户",example = "")
    private Integer tenantId;
    @Schema(description = "状态(1启用 0停用)",example = "")
    protected Integer status ;
    @Schema(description = "开始结束时间",example = "")
    protected List<String> beginAndEndTime;
    /**
     * 页码
     */
    @Schema(description = "页码",example = "1")
    protected Integer pageNum;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数",example = "10")
    protected Integer pageSize;
    @Schema(description = "开始时间")
    protected String searchStartTime;
    @Schema(description = "结束时间")
    protected String searchEndTime;

    public String getSearchStartTime() {
        this.dealDate();
        return searchStartTime;
    }


    public String getSearchEndTime() {
        this.dealDate();
        return searchEndTime;
    }

    /**
     * 处理查询日期
     * 1，一种 yyyy-MM-dd  补上开始和结束
     * 2，一种 yyyy-MM-dd HH:mm:ss 不处理
     */
    private void dealDate(){
        if (Objects.nonNull(this.searchStartTime) ){
            return;
        }
        //
        if (this.beginAndEndTime != null && this.beginAndEndTime.size() == 2){
            int length = this.beginAndEndTime.get(0).length();
            if ( length == 10){
                this.searchStartTime = String.format("%s 00:00:00",beginAndEndTime.get(0)) ;
            }
            length = this.beginAndEndTime.get(1).length();
            if ( length == 10){
                this.searchEndTime =  String.format("%s 23:59:59",beginAndEndTime.get(1)) ;
            }
        }
    }


}
