//package com.github.platform.core.sys.adapter.api.controller;
//
//import java.util.Map;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import com.github.platform.core.sys.adapter.api.command.DingCallbackCrypto;
//import com.github.platform.core.sys.application.executor.DingBackExecutorEnum;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author: hdy
// * @CreateDate: 2023/6/11 11:05
// * @Description: 钉钉事件回调
// */
//
//@Slf4j
//@RestController
//@RequestMapping("/ding")
//public class DingDingCallBackController {
//
//    private static final String AES_KEY = "cznqh4mGMKYKVQPNyHwG8bOS1cB1G3dUYjC4fxft9F6";
//
//    private static final String AES_TOKEN = "9XMFWKfVsBlUoPGiDkM9vyq8IGYJz8vpzckkEGdQb5Xp0ISvKOSpRAOccq";
//
//    private static final String APP_KEY = "dingqe9baw7vbprklahl";
//
//    @PostMapping("/callBack")
//    public Map<String, String> callBack(
//            @RequestParam(value = "msg_signature", required = false) String msg_signature,
//            @RequestParam(value = "timestamp", required = false) String timeStamp,
//            @RequestParam(value = "nonce", required = false) String nonce,
//            @RequestBody(required = false) JSONObject json) {
//        try {
//            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(AES_TOKEN, AES_KEY, APP_KEY);
//            // 3. 解密，并返回解密后的json
//            JSONObject eventJson = callbackCrypto.getDecryptMsg(msg_signature, timeStamp, nonce, json.getString("encrypt"));
//            log.warn("监听到钉钉事件回调,具体信息如下,eventJson:{}", JSON.toJSONString(eventJson));
//            String eventType = eventJson.getString("EventType");
//            // 4. 开始进行业务处理
//            DingBackExecutorEnum dingEnum = DingBackExecutorEnum.getDingBackExecutorEnum(eventType);
//            if(null == dingEnum){
//                return callbackCrypto.getEncryptedMap("success");
//            }
//            dingEnum.eventDeal(eventJson);
//            // 5. 返回success的加密数据
//            return callbackCrypto.getEncryptedMap("success");
//        } catch (Exception e) {
//            log.error("钉钉回调信息失败", e);
//        }
//        return null;
//    }
//}
