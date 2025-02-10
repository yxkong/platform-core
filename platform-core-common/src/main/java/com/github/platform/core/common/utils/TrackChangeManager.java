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
import java.util.*;
import java.util.stream.Collectors;

/**
 * 追踪工具
 * @author: yxkong
 * @date: 2024/6/7 14:05
 * @version: 1.0
 */
@Slf4j
public class TrackChangeManager {

    private List<TrackChangeRecord> changes ;
    /**源实体*/
    private Object original;
    /**修改后的实体*/
    private Object modified;
    private Integer level;
    /**是否新增*/
    private Boolean isNew;

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
        // 判断 original 是否为 null，如果为 null 则表示新增操作
        isNew = original == null;
        for (Field field : clazz.getDeclaredFields()) {
            TrackChange trackChange = field.getAnnotation(TrackChange.class);
            if (Objects.isNull(trackChange )) {
                continue;
            }
            try{
                field.setAccessible(true);
                // 如果是新增，原始值设置为 null
                Object originalValue = isNew ? null : field.get(this.original);
                Object modifiedValue = field.get(this.modified);
                // 如果目标值为null，不处理
                if (Objects.isNull(modifiedValue) || "".equals(modifiedValue.toString())){
                    continue;
                }
                // 判断是否需要记录变化
                boolean hasChanged = isNew || !Objects.equals(originalValue, modifiedValue) || StringUtils.isNotEmpty(trackChange.merge()) || Objects.equals(trackChange.merge(),"ignore");
                if (hasChanged) {
                    TrackChangeRecord trackChangeRecord = new TrackChangeRecord(
                            field.getName(),
                            trackChange.compare(),
                            trackChange.merge(),
                            originalValue,
                            modifiedValue,
                            trackChange.remark(),
                            trackChange.dateFormat(),
                            trackChange.sort()
                    );
                    changes.add(trackChangeRecord);
                }
            }catch (Exception e){
            }
        }
        // 检查是否需要继续处理父类
        if (!shouldContinueWithSuperclass(clazz)) {
            return;
        }
        initChanges(clazz.getSuperclass());
    }
    private boolean shouldContinueWithSuperclass(Class<?> clazz) {
        return !Objects.equals(clazz.getSuperclass(), Object.class) &&
                !Objects.equals(clazz.getSuperclass(), Serializable.class) &&
                !Objects.equals(clazz.getSuperclass(), BaseEntity.class) &&
                level <= 2;
    }

    /**
     * 获取改变结果
     * @param prefixSplit
     * @return
     */
    public String getChangeResult(String prefixSplit) {
        if (this.changes.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        // 提前缓存需要的 merge 数据
        Map<String, TrackChangeRecord> mergeCache = changes.stream()
                .filter(x -> StringUtils.isNotEmpty(x.getMerge()))
                .collect(Collectors.toMap(TrackChangeRecord::getFieldName, record -> record));

        changes.stream()
                .filter(s -> !Objects.equals(s.getMerge(), "ignore"))
                .sorted(Comparator.comparingInt(TrackChangeRecord::getSort))
                .forEach(s -> {
                    Object originalValue = s.getOriginalValue();
                    Object modifiedValue = s.getModifiedValue();

                    // 处理日期格式化
                    if (StringUtils.isNotEmpty(s.getDateFormat())) {
                        originalValue = LocalDateTimeUtil.parseDateTime(originalValue.toString(), s.getDateFormat());
                        modifiedValue = LocalDateTimeUtil.parseDateTime(modifiedValue.toString(), s.getDateFormat());
                    }

                    if (isNew) {
                        appendNewChange(sb, prefixSplit, s, modifiedValue);
                    } else {
                        appendExistingChange(sb, prefixSplit, s, originalValue, modifiedValue, mergeCache);
                    }
                });

        return sb.length() > 0 ? sb.substring(prefixSplit.length()) : sb.toString();
    }

    private void appendNewChange(StringBuilder sb, String prefixSplit, TrackChangeRecord s, Object modifiedValue) {
        if (Objects.nonNull(modifiedValue)) {
            sb.append(prefixSplit)
                    .append(s.getRemark())
                    .append(SymbolConstant.colon)
                    .append(modifiedValue);
        }
    }

    private void appendExistingChange(StringBuilder sb, String prefixSplit, TrackChangeRecord s,
                                      Object originalValue, Object modifiedValue, Map<String, TrackChangeRecord> mergeCache) {
        if (StringUtils.isNotEmpty(s.getMerge())) {
            TrackChangeRecord merge = mergeCache.get(s.getMerge());
            if (merge != null && hasMergedChanged(s, merge)) {
                sb.append(prefixSplit)
                        .append(s.getRemark())
                        .append("由【")
                        .append(s.getOriginalValue())
                        .append(SymbolConstant.colon)
                        .append(merge.getOriginalValue())
                        .append("】变为：【")
                        .append(s.getModifiedValue())
                        .append(SymbolConstant.colon)
                        .append(merge.getModifiedValue())
                        .append("】");
            }
        } else {
            if (s.isCompare()) {
                sb.append(prefixSplit)
                        .append(s.getRemark())
                        .append("由【")
                        .append(originalValue)
                        .append("】变为：【")
                        .append(modifiedValue)
                        .append("】");
            } else {
                if (Objects.nonNull(modifiedValue)) {
                    sb.append(prefixSplit)
                            .append(s.getRemark())
                            .append(SymbolConstant.colon)
                            .append(modifiedValue);
                }
            }
        }
    }

    private boolean hasMergedChanged(TrackChangeRecord s, TrackChangeRecord merge) {
        return !Objects.equals(s.getOriginalValue(), s.getModifiedValue()) ||
                !Objects.equals(merge.getOriginalValue(), merge.getModifiedValue());
    }

}
