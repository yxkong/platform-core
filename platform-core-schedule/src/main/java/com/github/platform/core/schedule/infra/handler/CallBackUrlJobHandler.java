package com.github.platform.core.schedule.infra.handler;

import com.github.platform.core.common.configuration.feign.customer.FeignService;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.schedule.domain.dto.SysJobDto;
import com.github.platform.core.standard.constant.HeaderConstant;
import com.github.platform.core.standard.entity.dto.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 回调统一处理
 * @author: yxkong
 * @date: 2023/9/19 10:09 AM
 * @version: 1.0
 */
@Component
@Slf4j
public class CallBackUrlJobHandler extends AbstractJobMonitorHandler {
    @Resource
    private FeignService feignService;
    @Override
    public Pair<Boolean, String> bizHandler(SysJobDto jobDto ) {
        Map<String, Object> requestMap = new HashMap<>();
        if (StringUtils.isNotEmpty(jobDto.getHandlerParam())){
            requestMap =  JsonUtils.toMap(jobDto.getHandlerParam());
        }
        Map<String,Object> header = new HashMap<>();
        header.put(HeaderConstant.ACCESS_TOKEN,jobDto.getAccessToken());

        ResultBean resultBean = feignService.url(jobDto.getCallBackUrl()).request(requestMap).header(header).restful().resultBean();
        if (log.isDebugEnabled()){
            log.warn("远程调用 url:{} 调用结果 {}/{}",jobDto.getCallBackUrl(),resultBean.getStatus(),resultBean.getMessage());
        }
        if (log.isWarnEnabled()){
            if (!resultBean.isSucc()){
                log.warn("远程调用 url:{} 调用结果 {}/{}",jobDto.getCallBackUrl(),resultBean.getStatus(),resultBean.getMessage());
            }
        }
        return Pair.of(resultBean.isSucc(),resultBean.getMessage());
    }
}
