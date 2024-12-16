package com.github.platform.core.gateway.admin.infra.util;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.gateway.admin.domain.common.entity.GatewayRouteBase;
import com.github.platform.core.gateway.admin.domain.common.entity.GatewayRouteConditionBase;
import com.github.platform.core.gateway.admin.domain.constant.ConditionEnum;
import com.github.platform.core.standard.constant.SymbolConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.*;

/**
 * 路由信息适配到网关
 * @Author: yxkong
 * @Date: 2024/8/21
 * @version: 1.0
 */
public class RouteInfoUtil {

    /**
     * 获取适配信息
     * @param basic
     * @param conditions
     * @return
     */
    public static Map<String, Object> getResult(GatewayRouteBase basic, List<? extends GatewayRouteConditionBase> conditions) {
        LinkedHashMap<String, Object> rst = new LinkedHashMap<>();
        rst.put("id",basic.getRouteId());
        rst.put("uri", URI.create(basic.getUri()));
        rst.put("order",basic.getSort());
        rst.put("metadata",new HashMap<>());
        if (CollectionUtil.isEmpty(conditions)){
            return rst;
        }
        // 处理 filters
        List<RouteCondition> filters = new ArrayList<>();
        // 处理 predicates
        List<RouteCondition> predicates = new ArrayList<>();
        // 对 conditions 按 sort 字段进行排序
        conditions.sort(Comparator.comparing(GatewayRouteConditionBase::getSort));
        conditions.forEach(condition -> {
                    Map<String, String> map = new LinkedHashMap<>();
                    ConditionEnum anEnum = ConditionEnum.getByName(condition.getName());
                    if (Objects.isNull(anEnum)){
                        return;
                    }
                    //针对自定义filter特殊处理
                    if (anEnum.equals(ConditionEnum.CUSTOM)){
                        String[] splits = condition.getArgs().split(anEnum.getSplit());
                        condition.setName(splits[0]);
                        map.put(anEnum.getSplit0(), splits[0]);
                        for (int i = 1; i < splits.length ; i++) {
                            if (splits[i].contains(SymbolConstant.colon)){
                                String[] args = splits[i].split(SymbolConstant.colon);
                                map.put(args[0], args[1]);
                            }
                        }
                    } else if (anEnum.getLength() == 1){
                        map.put(anEnum.getSplit0(),condition.getArgs());
                    } else if (anEnum.getLength() >= 2){
                        String[] splits = condition.getArgs().split(anEnum.getSplit());
                        map.put(anEnum.getSplit0(), splits[0]);
                        map.put(anEnum.getSplit1(), splits[1]);
                    }
                    if (anEnum.isFilter()){
                        filters.add(new RouteCondition(condition.getName(), map));
                    } else {
                        predicates.add(new RouteCondition(condition.getName(), map));
                    }
                });
        rst.put("filters",filters);
        rst.put("predicates",predicates);
        return rst;
    }
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class RouteCondition{
        private String name;
        private Map<String, String> args = new LinkedHashMap();
    }
}
