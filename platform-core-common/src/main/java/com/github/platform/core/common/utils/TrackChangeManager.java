package com.github.platform.core.common.utils;

import com.github.platform.core.common.annotation.TrackChange;
import com.github.platform.core.common.entity.TrackChangeRecord;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.BaseEntity;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 追踪工具
 * @author: yxkong
 * @date: 2024/6/7 14:05
 * @version: 1.0
 */
@Slf4j
public class TrackChangeManager {

    private List<TrackChangeRecord> changes ;

    private Object original;
    private Object modified;
    private Integer level;

    public TrackChangeManager(Object original, Object modified) {
        this.changes = new ArrayList<>();
        this.original = original;
        this.modified = modified;
        this.level = 1;
        initChanges(Objects.nonNull(original) ? original.getClass() : modified.getClass());
    }

    private void initChanges(Class<?> clazz) {
        if (clazz == null) {
            return;
        }
        for (Field field : clazz.getDeclaredFields()) {
            TrackChange trackChange = field.getAnnotation(TrackChange.class);
            if (Objects.isNull(trackChange )) {
                continue;
            }
            try{
                field.setAccessible(true);
                Object originalValue = field.get(this.original);
                Object modifiedValue = field.get(this.modified);
                // 如果目标值为null，不处理
                if (Objects.isNull(modifiedValue) || "".equals(modifiedValue.toString())){
                    continue;
                }
                if (!Objects.equals(originalValue,modifiedValue) || StringUtils.isNotEmpty(trackChange.merge())) {
                    TrackChangeRecord trackChangeRecord = new TrackChangeRecord(field.getName(),trackChange.compare(),trackChange.merge(), originalValue, modifiedValue, trackChange.remark(), trackChange.dateFormat(), trackChange.sort());
                    changes.add(trackChangeRecord) ;
                }
            }catch (Exception e){
            }
        }
        if (Objects.equals(clazz.getSuperclass(),Object.class) || Objects.equals(clazz.getSuperclass(), Serializable.class) || Objects.equals(clazz.getSuperclass(), BaseEntity.class) || level>2){
            return;
        }
        initChanges(clazz.getSuperclass());
    }

    /**
     * 获取改变结果
     * @param prefixSplit
     * @return
     */
    public String getChangeResult(String prefixSplit){
        if (this.changes.isEmpty()){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        changes.stream()
                .filter(s-> !Objects.equals(s.getMerge(),"ignore"))
                .sorted((a,b)->Integer.compare(a.getSort(),b.getSort()))
                .forEach(s->{
                    if (StringUtils.isNotEmpty(s.getMerge())){
                        TrackChangeRecord merge = changes.stream().filter(x -> x.getFieldName().equals(s.getMerge())).findAny().get();
                        if (!Objects.equals(s.getOriginalValue(),s.getModifiedValue()) || !Objects.equals(merge.getOriginalValue(),merge.getModifiedValue())){

                            sb.append(s.getRemark()).append("由【");
                            sb.append(s.getOriginalValue()).append(SymbolConstant.colon).append(merge.getOriginalValue()).append("】变为：【")
                                    .append(s.getModifiedValue()).append(SymbolConstant.colon).append(merge.getModifiedValue()).append("】");
                        }
                    } else{
                        if (s.isCompare()) {
                            sb.append(prefixSplit);
                            sb.append(s.getRemark()).append("由【");
                            if (StringUtils.isNotEmpty(s.getDateFormat())){
                                LocalDateTime originalDate = LocalDateTimeUtil.parseDateTime(s.getOriginalValue().toString(), s.getDateFormat());
                                LocalDateTime modifiedDate = LocalDateTimeUtil.parseDateTime(s.getModifiedValue().toString(), s.getDateFormat());
                                sb.append(originalDate).append("】变为：【").append(modifiedDate);
                            } else {
                                sb.append(s.getOriginalValue()).append("】变为：【").append(s.getModifiedValue());
                            }
                            sb.append("】");
                        } else{
                            if(Objects.nonNull(s.getModifiedValue())){
                                sb.append(prefixSplit);
                                sb.append(s.getRemark()).append(SymbolConstant.colon).append(s.getModifiedValue());
                            }
                        }
                    }

                });
        return sb.length()>0 ? sb.substring(prefixSplit.length()-1) : sb.toString();
    }
}
