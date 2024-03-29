package com.github.platform.core.sms.infra.rpc.feign.interceptor;

import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.sms.SysSmsServiceProviderMapper;
import com.github.platform.core.sms.domain.common.entity.SysSmsServiceProviderBase;
import com.github.platform.core.sms.infra.rpc.constants.CtYunConstant;
import com.github.platform.core.sms.infra.rpc.constants.SMSConstant;
import com.github.platform.core.sms.infra.utils.SmsPwdUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.exception.ConfigRuntimeException;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 天翼云短信拦截器，处理统一header
 * @author: yxkong
 * @date: 2023/2/17 4:34 PM
 * @version: 1.0
 */
@Slf4j
public class CtyunInterceptor implements RequestInterceptor {
    private SysSmsServiceProviderMapper serviceProviderMapper;

    public CtyunInterceptor(SysSmsServiceProviderMapper serviceProviderMapper) {
        this.serviceProviderMapper = serviceProviderMapper;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        byte[] body = requestTemplate.body();
        SysSmsServiceProviderBase serviceProviderDO = serviceProviderMapper.findByProNo(SMSConstant.CTY_PRO_NO);
        if (Objects.isNull(serviceProviderDO)){
            throw new ConfigRuntimeException(ResultStatusEnum.CONFIG_ERROR.getStatus(),"未找到有效的天翼云厂商配置，请核查");
        }

        String accessKey = serviceProviderDO.getAccount();
        String securityKey = SmsPwdUtil.me.decode( serviceProviderDO.getEncryptPwd(),serviceProviderDO.getSalt());

        String signatureTime = LocalDateTimeUtil.dateTime("yyyyMMdd'T'HHmmss'Z'");
        String signatureDate = LocalDateTimeUtil.dateTime("yyyyMMdd");

        // 构造请求流水号
        String uuId = StringUtils.uuidRmLine();
        // 构造待签名字符串
        String campHeader = String.format("ctyun-eop-request-id:%s\neop-date:%s\n", uuId, signatureTime);
        // 报文原封不动进行sha256摘要
        String calculateContentHash = getSha256(new String(body));
        String afterQuery = getAfterQuery(serviceProviderDO.getInterfaceUrl());
        String signatureStr = campHeader + "\n" + afterQuery + "\n" + calculateContentHash;
        // 构造签名
        byte[] kTime = hmacSha256(signatureTime.getBytes(), securityKey.getBytes());
        byte[] kAk = hmacSha256(accessKey.getBytes(), kTime);
        byte[] kDate = hmacSha256(signatureDate.getBytes(), kAk);
        String signature = Base64.getEncoder().encodeToString(hmacSha256(signatureStr.getBytes(StandardCharsets.UTF_8), kDate));
        /**signHeader
         * 加密参数，包括三部分：
         * 1、AK;
         * 2、参与加密的请求头Headers，默认为ctyun-eop-request-id，eop-date，且为必须添加的参数;
         * 3、加密结果Signature
         * 以空格分开
         */
        String signHeader = String.format("%s Headers=ctyun-eop-request-id;eop-date Signature=%s", accessKey, signature);
        log.info("body:"+new String(body)+"\n signatureTime:"+ signatureTime + "\n signHeader:"+signHeader+"\n uuId:"+uuId);
        requestTemplate.header(CtYunConstant.HEADER_CONTENT_TYPE_KEY, CtYunConstant.HEADER_CONTENT_TYPE_VAL);
        requestTemplate.header(CtYunConstant.HEADER_EOP_DATE_KEY, signatureTime);
        requestTemplate.header(CtYunConstant.HEADER_EOP_AUTHORIZATION_KEY, signHeader);
        requestTemplate.header(CtYunConstant.HEADER_CTYUN_EOP_REQUEST_ID_KEY, uuId);
    }
    private String getAfterQuery(String urlStr) {
        StringBuilder afterQuery = new StringBuilder();

        try {
            URL url = new URL(urlStr);
            String query = url.getQuery();

            if (query != null) {
                String[] params = query.split(SymbolConstant.and);
                Arrays.sort(params);
                afterQuery.append(String.join(SymbolConstant.and, params));
            }
        } catch (MalformedURLException e) {
            log.error("构建参数异常：", e);
        }

        return afterQuery.toString();
    }

    private String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        int len = data.length;
        for (int i = 0; i < len; ++i) {
            byte b = data[i];
            String hex = Integer.toHexString(b);
            if (hex.length() == 1) {
                sb.append("0");
            } else if (hex.length() == 8) {
                hex = hex.substring(6);
            }
            sb.append(hex);
        }
        return sb.toString().toLowerCase(Locale.getDefault());
    }

    private String getSha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes(StandardCharsets.UTF_8));
            return toHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private byte[] hmacSha256(byte[] data, byte[] key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data);
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
