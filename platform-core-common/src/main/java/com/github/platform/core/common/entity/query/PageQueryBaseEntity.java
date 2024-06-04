package com.github.platform.core.common.entity.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Objects;

/**
 * 后台管理页面查询通用基类,会把页面上传递过来的beginAndEndTime 解析成 开始和结束时间
 * @author: yxkong
 * @date: 2023/7/27 8:43 下午
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class PageQueryBaseEntity extends BaseAdminEntity {

    @Schema(description = "开始结束时间，接收页面传递过来的时间",example = "")
    protected transient List<String> dateRange;

    /**
     * 页码
     */
    @Schema(description = "页码",example = "1")
    protected  Integer pageNum;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数",example = "10")
    protected  Integer pageSize;
    /**
     * 获取起始的偏移量
     * @return
     */
    @JsonIgnore
    public int getStartOffset(){
        if (Objects.nonNull(this.pageSize) && Objects.nonNull(this.pageSize)){
            return this.pageSize* (this.pageNum-1);
        }
        return 0;
    }
    @Override
    public String getSearchStartTime() {
        getSearchTime();
        return searchStartTime;
    }
    @Override
    public String getSearchEndTime() {
        getSearchTime();
        return searchEndTime;
    }

    private void getSearchTime() {
        Pair<String, String> pair = this.dealDate(this.dateRange, this.searchStartTime, this.searchEndTime);
        this.searchStartTime = pair.getLeft();
        this.searchEndTime = pair.getRight();
    }

    /**
     * 处理查询日期
     * 1，一种 yyyy-MM-dd  补上开始和结束
     * 2，一种 yyyy-MM-dd HH:mm:ss 不处理
     */
    protected Pair<String,String> dealDate(List<String> dateRange, String start, String end){
        if (Objects.nonNull(start) || Objects.nonNull(end)){
            return Pair.of(start,end);
        }
        //
        if (dateRange != null && dateRange.size() == 2){
            String s = dateRange.get(0);
            if ( s.length() == 10){
                start = String.format("%s 00:00:00",s) ;
            } else {
                start = s;
            }
           s = dateRange.get(1);
            if ( s.length() == 10){
                end =  String.format("%s 23:59:59", s) ;
            } else {
                end = s;
            }
        }
        return Pair.of(start,end);
    }


}
