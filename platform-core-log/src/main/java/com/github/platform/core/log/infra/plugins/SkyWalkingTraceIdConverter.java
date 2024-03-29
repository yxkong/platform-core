//package com.github.platform.core.log.infra.plugins;
//
//import com.github.platform.core.common.utils.CollectionUtil;
//import org.apache.logging.log4j.core.LogEvent;
//import org.apache.logging.log4j.core.config.plugins.Plugin;
//import org.apache.logging.log4j.core.pattern.ConverterKeys;
//import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
//import org.apache.logging.log4j.util.ReadOnlyStringMap;
//
///**
// * log4j2 skyWalking扩展
// * @author: yxkong
// * @date: 2023/11/23 11:08
// * @version: 1.0
// */
//@Plugin(name = "skyWalkingTraceIdConverter",category = "Converter")
//@ConverterKeys({"traceId"})
//public class SkyWalkingTraceIdConverter extends LogEventPatternConverter {
//
//
//    protected SkyWalkingTraceIdConverter(String name,String style){
//        super(name,style);
//    }
//    public static SkyWalkingTraceIdConverter newInstance(String[] options){
//        return new SkyWalkingTraceIdConverter("traceId","traceId");
//    }
//    @Override
//    public void format(LogEvent event, StringBuilder toAppendTo) {
//        ReadOnlyStringMap map  = event.getContextData();
//        if (CollectionUtil.isNotEmpty(map)){
//            map.getValue()
//        }
//    }
//}
