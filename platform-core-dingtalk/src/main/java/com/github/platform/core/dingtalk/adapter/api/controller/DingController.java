package com.github.platform.core.dingtalk.adapter.api.controller;

import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.dingtalk.adapter.api.command.DingInitCmd;
import com.github.platform.core.dingtalk.domain.gateway.IDingTalkGateway;
import com.github.platform.core.dingtalk.infra.configuration.DingProperties;
import com.github.platform.core.log.infra.annotation.OptLog;
import com.github.platform.core.standard.entity.dto.ResultBean;
import com.github.platform.core.web.web.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 钉钉接口
 * @author: yxkong
 * @date: 2024/1/23 18:33
 * @version: 1.0
 */
@RestController
@Tag(name = "form",description = "表单配置管理")
@RequestMapping("api/dingtalk")
@Slf4j
public class DingController extends BaseController {
    @Resource
    private IDingTalkGateway dingTalkGateway;
    @Resource
    private DingProperties dingProperties;

    @OptLog(module="form",title="查询表单配置列表",persistent = false)
    @Operation(summary = "查询表单配置列表",tags = {"form"})
    @PostMapping("/initDept")
    public ResultBean initDept(@RequestBody DingInitCmd cmd){
        if (AuthUtil.isSuperAdmin()){
            dingTalkGateway.initDept(cmd.getDeptId(),cmd.getTenantId(), LoginUserInfoUtil.getLoginName());
            return buildSucResp();
        }else {
            return buildSimpleResp(false,"没有操作权限");
        }
    }
    @OptLog(module="form",title="查询表单配置列表",persistent = false)
    @Operation(summary = "查询表单配置列表",tags = {"form"})
    @PostMapping("/initAllUser")
    public ResultBean initAllUser(@RequestBody DingInitCmd cmd){
        if (AuthUtil.isSuperAdmin()){
            dingTalkGateway.initAllUser(cmd.getTenantId(),LoginUserInfoUtil.getLoginName());
            return buildSucResp();
        }else {
            return buildSimpleResp(false,"没有操作权限");
        }
    }

//    @PostMapping("/callBack")
//    public Map<String, String> callBack(
//            @RequestParam(value = "msg_signature", required = false) String msg_signature,
//            @RequestParam(value = "timestamp", required = false) String timeStamp,
//            @RequestParam(value = "nonce", required = false) String nonce,
//            @RequestBody(required = false) JSONObject json) {
//        try {
//            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(dingProperties.getToken(), dingProperties.getEncodingAesKey(), dingProperties.getCorpId());
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

}
