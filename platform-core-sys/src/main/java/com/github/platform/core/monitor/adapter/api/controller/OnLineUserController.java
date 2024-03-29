package com.github.platform.core.monitor.adapter.api.controller;

import com.github.platform.core.auth.annotation.RequiredRoles;
import com.github.platform.core.auth.constant.RoleConstant;
import com.github.platform.core.auth.constants.AuthTypeEnum;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.monitor.adapter.api.command.OnLineQuery;
import com.github.platform.core.monitor.adapter.api.command.SendToUserCmd;
import com.github.platform.core.monitor.adapter.api.convert.OnLineUserConvert;
import com.github.platform.core.monitor.domain.dto.OnLineUserDto;
import com.github.platform.core.monitor.domain.ws.InMessage;
import com.github.platform.core.monitor.infra.websocket.constant.WsConstant;
import com.github.platform.core.monitor.infra.websocket.service.IWsMessageService;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.standard.util.ResultBeanUtil;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 在线用户
 * @author: yxkong
 * @date: 2023/6/8 5:03 PM
 * @version: 1.0
 */
@RestController
@Tag(name = "online",description = "在线用户")
@RequestMapping("/sys/monitor/online")
@Slf4j
public class OnLineUserController extends BaseController {
    @Resource
    private ICacheService cacheService;
    @Resource
    private ITokenService tokenService;
    @Resource
    private OnLineUserConvert convert;
    @Autowired
    private Map<String, IWsMessageService> wsMessageServiceMap;
    /**
     * 查询在线用户
     * @param query
     * @return
     */
    @Operation(summary = "查询在线用户",tags = {"online"})
    @PostMapping("/query")
    public ResultBean<PageBean<OnLineUserDto>> query(@RequestBody OnLineQuery query){
        Long max = query.getStart();

        long min = 0;
        if ( null == max){
            max = System.currentTimeMillis();
        }
        min = max- WsConstant.retainMins;
        int offset = query.getPageNum() -1 ;
        if (offset < 0 ){
            offset = 0;
        }
        Set<ZSetOperations.TypedTuple<String>> tuples = cacheService.zRangeByScoreWithScores(CacheConstant.onlineUsers, min, max, offset, query.getPageSize());
        if (CollectionUtil.isEmpty(tuples)){
            return ResultBeanUtil.success();
        }
        Map<String, Double> map = tuples.stream().collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
        List<String> nameAndTokens = new ArrayList<>(map.keySet());
        //redis的keys
        List<String> keys = new ArrayList<>();
        List<String> names = new ArrayList<>();
        nameAndTokens.forEach(nameAndToken ->{
            String[] split = nameAndToken.split(SymbolConstant.colon);
            names.add(split[0]);
            keys.add(tokenService.getTokenKey(AuthTypeEnum.SYS,split[1]));
        });
        //这里的key得组装
        List<String> list = cacheService.multiGet(keys);
        List<OnLineUserDto> rst = new ArrayList<>();
        for (int i = 0; i < nameAndTokens.size(); i++) {
            String loginName = names.get(i);
            String loginStr = list.get(i);
            OnLineUserDto dto = null;
            //正常情况下不会出现这种退出的情况，退出会删除token，异步，高并发会有延迟
            if (StringUtils.isNotEmpty(loginStr)){
                LoginUserInfo loginUserInfo = JsonUtils.fromJson(loginStr, LoginUserInfo.class);
                String lastTime = LocalDateTimeUtil.formatTimeStamp(map.get(nameAndTokens.get(i)).longValue());
                dto = convert.toDto(loginUserInfo, lastTime);
                dto.setStatus("在线");
            } else {
                dto = OnLineUserDto.builder().userName(loginName).status("已退出").build();
            }
            rst.add(dto);
        }
        long count = cacheService.zCount(CacheConstant.onlineUsers, min, max);
        return ResultBeanUtil.succ(new PageBean(query.getPageNum(),count,query.getPageSize(),rst));
    }
    /**
     * 查询在线用户
     * @param cmd
     * @return
     */
    @Operation(summary = "发送消息给用户",tags = {"online"})
    @RequiredRoles(value = {RoleConstant.SUPER_ROLE})
    @PostMapping("/sendToUser")
    public ResultBean sendToUser(@RequestBody SendToUserCmd cmd){
        InMessage inMessage = convert.toInMsg(cmd);
        inMessage.setSendTime(LocalDateTimeUtil.dateTime());
        /**获取接收用户的登录信息*/
        if (inMessage.isAll()){
            return ResultBeanUtil.fail("暂不支持批量操作！",null);
        }
        //获取用户登录信息
        String loginInfo = tokenService.getLoginInfoStrByLoginName(AuthTypeEnum.SYS,cmd.getToUser());
        inMessage.setLoginInfoStr(loginInfo);
        inMessage.setFromUser(LoginUserInfoUtil.getLoginName());
        String mapKey = inMessage.getBizType()+"WsMessageService";
        boolean executor = wsMessageServiceMap.get(mapKey).executor(inMessage,true);
        return ResultBeanUtil.success();
    }

}
