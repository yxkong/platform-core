package com.github.platform.core.kafka;

import brave.Tracing;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.kafka.entity.BigDataMsgContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 大数据客户
 * 大数据的采集，是写到log文件中，在log4j2中配置对应的日志收集器，收集到kafka中
 * @author: yxkong
 * @date: 2023/8/24 11:45 AM
 * @version: 1.0
 */
public class BigDataSaveClient {
    private static Logger log = LoggerFactory.getLogger("bigDataSaveLogger");
    public static void sendMsgToBigData(BigDataMsgContent<?> content, Integer tenantId) {
        if (tenantId == null) {
            log.error("保存数据到大数据异常{未传tenantId}:{}", JsonUtils.toJson(content));
        } else if (StringUtils.isEmpty(content.getTableName())) {
            log.error("保存数据到大数据异常{未指定表名}:{}", JsonUtils.toJson(content));
        } else if (StringUtils.isEmpty(content.getDeviceId()) && StringUtils.isEmpty(content.getCertId()) && StringUtils.isEmpty(content.getToken()) && StringUtils.isEmpty(content.getMobile()) && content.getCustomerId() == null) {
            log.error("保存数据到大数据异常{customerId，certId，mobile,token,deviceId 均为空，添加用户唯一标识}:{}", JsonUtils.toJson(content));
        } else if (content.getData() == null) {
            log.error("保存数据到大数据异常{实体对象为空}:{}", JsonUtils.toJson(content));
        } else {
            content.setTenantId(tenantId);
            content.setTraceId(getTraceId());
            log.warn(JsonUtils.toJson(content));
            if (log.isInfoEnabled()) {
                log.info("保存数据到大数据：data:{}", JsonUtils.toJson(content));
            }

        }
    }

    private static String getTraceId() {
        try {
            return Tracing.currentTracer().currentSpan().context().traceIdString();
        } catch (Exception var1) {
            return null;
        }
    }
}
