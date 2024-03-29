package com.github.platform.core.standard.entity.query;

import com.github.platform.core.standard.entity.BaseAdminEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 后台管理页面查询通用基类,会把页面上传递过来的beginAndEndTime 解析成 开始和结束时间
 * 已转移到 common模块 {@link com.github.platform.core.common.entity.query.PageQueryBaseEntity }  提供了id解密的能力
 * @author: yxkong
 * @date: 2023/7/27 8:43 下午
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Deprecated
public class PageQueryBaseEntity extends BaseAdminEntity {
    @Schema(description = "开始结束时间，接收页面传递过来的时间",example = "")
    protected transient List<String> beginAndEndTime;
    @Override
    public String getSearchStartTime() {
        if (StringUtils.isNotEmpty(this.searchStartTime)){
            return this.searchStartTime;
        }
        this.dealDate();
        return searchStartTime;
    }


    @Override
    public String getSearchEndTime() {
        if (StringUtils.isNotEmpty(this.searchEndTime)){
            return this.searchEndTime;
        }
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
