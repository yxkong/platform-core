package com.github.platform.core.gateway.admin.application.executor.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.constant.SpringBeanNameConstant;
import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.service.IPublishService;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.gateway.admin.application.executor.IGatewayRouteExecutor;
import com.github.platform.core.gateway.admin.domain.constant.GatewayOptEnum;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteConditionContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteInfoContext;
import com.github.platform.core.gateway.admin.domain.context.GatewayRouteQueryContext;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteConditionDto;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteDto;
import com.github.platform.core.gateway.admin.domain.dto.GatewayRouteInfoDto;
import com.github.platform.core.gateway.admin.domain.gateway.IGatewayRouteConditionGateway;
import com.github.platform.core.gateway.admin.domain.gateway.IGatewayRouteGateway;
import com.github.platform.core.gateway.admin.infra.util.RouteInfoUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.entity.dto.CommonPublishDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 网关路由执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-08-13 14:58:53.823
 * @version 1.0
 */
@Service
@Slf4j
public class GatewayRouteExecutorImpl extends BaseExecutor implements IGatewayRouteExecutor {
    @Resource
    private IGatewayRouteGateway gatewayRoutesGateway;
    @Resource
    private IGatewayRouteConditionGateway gatewayRouteConditionGateway;

    @Autowired(required = false)
    @Qualifier(SpringBeanNameConstant.REDIS_PUBLISH_SERVICE)
    private IPublishService publishService;
    /**
    * 查询网关路由列表
    * @param context 查询上下文
    * @return 分页结果
    */
    @Override
    public PageBean<GatewayRouteDto> query(GatewayRouteQueryContext context){
        return gatewayRoutesGateway.query(context);
    };
    /**
    * 新增网关路由
    * @param context 新增上下文
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertInfo(GatewayRouteInfoContext context){

        GatewayRouteContext routeBasic = context.getRouteBasic();
        GatewayRouteDto record = gatewayRoutesGateway.insert(routeBasic);
        if (Objects.isNull(record.getId())){
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        List<GatewayRouteConditionContext> conditions = context.getConditions();
        conditions.forEach(s->{
            s.setCreateBy(LoginUserInfoUtil.getLoginName());
        });
        gatewayRouteConditionGateway.insertList(conditions, record.getId());
        sendRedisEvent(context, GatewayOptEnum.ROUTE_ADD);
        return record.getStrId();
    }

    /**
     * 发送事件
     * @param context 路由信息上下文
     * @param optEnum 操作枚举
     * @return
     */
    private boolean sendRedisEvent(GatewayRouteInfoContext context, GatewayOptEnum optEnum) {
        CommonPublishDto dto = new CommonPublishDto();
        dto.setHandlerBean(optEnum.getHandlerBeanName());
        dto.setModule("gateway");
        dto.setNode(optEnum.getOpt());
        dto.setSendTime(LocalDateTimeUtil.dateTime());
        dto.setTargetService("gateway");
        dto.setSourceService("gatewayAdmin");
        dto.setLoginName(LoginUserInfoUtil.getLoginName());
        dto.setData(JsonUtils.toJson(RouteInfoUtil.getResult(context.getRouteBasic(), context.getConditions())));
        if (Objects.nonNull(publishService)){
            return publishService.publish(dto);
        }
        log.error("未找到{},请配置platform.publish.enabled 为true开启redis订阅发布功能",SpringBeanNameConstant.REDIS_PUBLISH_SERVICE);
        return false;
    }
    /**
    * 根据id查询网关路由明细
    * @param id 主键
    * @return 单条记录
    */
    @Override
    public GatewayRouteDto findById(Long id) {
        return gatewayRoutesGateway.findById(id);
    }
    /**
    * 修改网关路由
    * @param context 更新上下文
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(GatewayRouteInfoContext context) {
        GatewayRouteContext routeBasic = context.getRouteBasic();
        Pair<Boolean, GatewayRouteDto> update = gatewayRoutesGateway.update(routeBasic);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
        handlerConditions(context.getConditions(), routeBasic.getId());
        sendRedisEvent(context, GatewayOptEnum.ROUTE_UPDATE);
    }
    private void handlerConditions(List<GatewayRouteConditionContext> list,Long routeId) {
        LocalDateTime now = LocalDateTimeUtil.dateTime();
        String loginUserName = LoginUserInfoUtil.getLoginName();

        // 获取已有的条件
        List<GatewayRouteConditionDto> existConditions = gatewayRouteConditionGateway.findByRouteId(routeId);
        Set<Long> existConditionIds = existConditions.stream()
                .map(GatewayRouteConditionDto::getId)
                .collect(Collectors.toSet());


        List<GatewayRouteConditionContext> inserts = new ArrayList<>();
        List<GatewayRouteConditionContext> updates = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)){
            // 使用stream对数据进行插入和更新的分类
            Map<Boolean, List<GatewayRouteConditionContext>> partitionedData = list.stream()
                    .peek(item -> {
                        item.setUpdateTime(now);
                        item.setUpdateBy(loginUserName);
                        item.setRouteId(routeId);
                        if (item.getId() == null) {
                            item.setCreateTime(now);
                            item.setCreateBy(loginUserName);
                        }
                    })
                    .collect(Collectors.partitioningBy(item -> item.getId() == null));
            inserts = partitionedData.get(true);
            updates = partitionedData.get(false);
        }
        // 获取需要删除的条件ID
        Set<Long> updateIds = updates.stream()
                .map(GatewayRouteConditionContext::getId)
                .collect(Collectors.toSet());

        Set<Long> deleteIds = existConditionIds.stream()
                .filter(id -> !updateIds.contains(id))
                .collect(Collectors.toSet());
        // 删除不存在的条件
        if (CollectionUtil.isNotEmpty(deleteIds)) {
            gatewayRouteConditionGateway.deleteByIds(deleteIds.toArray(new Long[0]));
        }

        // 插入新的条件
        if (CollectionUtil.isNotEmpty(inserts)) {
            gatewayRouteConditionGateway.insertList(inserts, routeId);
        }

        // 更新已有的条件
        if (CollectionUtil.isNotEmpty(updates)) {
            gatewayRouteConditionGateway.updateList(updates, routeId);
        }
    }
    /**
    * 根据id删除网关路由记录
    * @param id 主键
    */
    @Override
    public void delete(Long id) {
        /**此处是为了再gateway上做多条件缓存，如果有必要，先查，后设置值*/
        GatewayRouteDto routeDto = gatewayRoutesGateway.findById(id);
        if (Objects.isNull(routeDto)){
            throw exception(ResultStatusEnum.NO_DATA);
        }
        GatewayRouteContext context = GatewayRouteContext.builder()
                .id(id).routeId(routeDto.getRouteId())
                .uri(routeDto.getUri())
                .sort(routeDto.getSort())
                .build();
        int d = gatewayRoutesGateway.delete(context);
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
        GatewayRouteInfoContext routeInfoContext = new GatewayRouteInfoContext();
        routeInfoContext.setRouteBasic(context);
        sendRedisEvent(routeInfoContext, GatewayOptEnum.ROUTE_DELETE);
    }

    @Override
    public GatewayRouteInfoDto findRouteInfo(Long id) {
        GatewayRouteDto route = gatewayRoutesGateway.findById(id);
        List<GatewayRouteConditionDto> list = gatewayRouteConditionGateway.findByRouteId(id);
        return new GatewayRouteInfoDto(route,list);
    }
}
