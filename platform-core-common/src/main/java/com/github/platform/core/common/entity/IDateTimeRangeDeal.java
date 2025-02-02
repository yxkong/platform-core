package com.github.platform.core.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.util.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 时间区间处理器
 * @author: yxkong
 * @date : 2023/12/19 18:11
 * @version: 1.0
 */
public interface IDateTimeRangeDeal {
    List<String> getDateTimeRange();
    @JsonIgnore
    default LocalDateTime getDateRangeStartTime(){
        if (CollectionUtil.isEmpty(this.getDateTimeRange()) || this.getDateTimeRange().size() != 2){
            return null;
        }
        String s = this.getDateTimeRange().get(0);
        if (Objects.isNull(s)){
            return null;
        }
        int length = s.length();
        if ( length == 10){
            return LocalDateTimeUtil.parseDefaultShort(String.format("%s 00:00:00", s)) ;
        } else {
            return LocalDateTimeUtil.parseDefaultShort(s);
        }
    }
    @JsonIgnore
    default LocalDateTime getDateRangeEndTime(){
        if (CollectionUtil.isEmpty(this.getDateTimeRange()) || this.getDateTimeRange().size() != 2){
            return null;
        }
        String s = this.getDateTimeRange().get(1);
        if (Objects.isNull(s)){
            return null;
        }
        int length = s.length();
        if ( length == 10){
            return LocalDateTimeUtil.parseDefaultShort(String.format("%s 00:00:00", s)) ;
        } else {
            return LocalDateTimeUtil.parseDefaultShort(s);
        }
    }
}
