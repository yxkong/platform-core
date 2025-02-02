package com.github.platform.core.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.standard.util.LocalDateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 时间区间处理器
 * @uthor: yxkong
 * @date : 2023/12/19 18:11
 * @version: 1.0
 */
public interface IDateRangeDeal {
    List<String> getDateRange();
    @JsonIgnore
    default LocalDate getDateRangeStart(){
        if (CollectionUtil.isEmpty(this.getDateRange()) || this.getDateRange().size() != 2){
            return null;
        }
        String s = this.getDateRange().get(0);
        if (Objects.isNull(s)){
            return null;
        }
        return LocalDateUtil.parseDefault(s);
    }
    @JsonIgnore
    default LocalDate getDateRangeEnd(){
        if (CollectionUtil.isEmpty(this.getDateRange()) || this.getDateRange().size() != 2){
            return null;
        }
        String s = this.getDateRange().get(1);
        if (Objects.isNull(s)){
            return null;
        }
        return LocalDateUtil.parseDefault(s);
    }
}
