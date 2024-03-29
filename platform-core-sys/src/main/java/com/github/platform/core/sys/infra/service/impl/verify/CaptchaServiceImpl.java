package com.github.platform.core.sys.infra.service.impl.verify;

import com.github.platform.core.cache.infra.service.ICacheService;
import com.github.platform.core.common.service.BaseServiceImpl;
import com.github.platform.core.common.utils.IdWorker;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.Base64;
import com.github.platform.core.sys.application.dto.VerifyCodeResult;
import com.github.platform.core.sys.domain.constant.CaptchaTypeEnum;
import com.github.platform.core.sys.domain.constant.Constants;
import com.github.platform.core.sys.infra.configuration.kaptcha.KaptchaProperties;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.service.ICaptchaService;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码服务，根据配置统一调度
 * @author: yxkong
 * @date: 2022/4/26 8:25 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class CaptchaServiceImpl extends BaseServiceImpl implements ICaptchaService {
    @Resource
    private KaptchaProperties properties;
    @Resource
    private IdWorker idWorker;
    @Resource
    private Producer producer;
    @Resource
    private ICacheService cacheService;


    /**
     * 根据用户名和类型生成图形验证码
     * @param bizNo 业务号，可选
     * @param captchaType 验证码生成类型
     * @return
     */
    @Override
    public VerifyCodeResult createImage(String bizNo, CaptchaTypeEnum captchaType){

        //认证序列号
        String verifySeq = idWorker.nextId()+"";
        String code = null;
        BufferedImage  image = null;
        if (properties.isMath()){
            String text = producer.createText();
            String[] split = text.split(SymbolConstant.at);
            code = split[1];
            image = producer.createImage(split[0]);
        } else if(properties.isText()){
            code = producer.createText();
            image = producer.createImage(code);
        }
        if (!Constants.INIT.equals(bizNo)){
            String key = properties.getKey(bizNo, verifySeq);
            cacheService.set(key,code,properties.getCache().getTimeout());
            if (log.isDebugEnabled()){
                log.debug("缓存key 为：{} val 为：{}",key,code);
            }
        } else {
            log.info("init captcha");
        }


        return VerifyCodeResult.builder().verifySeq(verifySeq).images(getBase64Images(image)).build();
    }


    /**
     * 验证验证码
     * @param bizNo 用户名
     * @param verifySeq 验证流水号
     * @param code 用户输入码
     * @return
     */
    @Override
    public Boolean check(String bizNo, String verifySeq, String code){
        String key = properties.getKey(bizNo, verifySeq);
        Object o = cacheService.get(key);
        if(log.isDebugEnabled()){
            log.debug("获取redis的key为{} val为{}",key,o);
        }

        if (null == o){
            if(log.isWarnEnabled()){
                log.warn("未获取到流水号：{} 对应的值",verifySeq);
            }
            exception(SysInfraResultEnum.VERIFY_CODE_EXPIRE);
        }
        try {
            String redisCode = (String)o;
            if (redisCode.equalsIgnoreCase(code)){
                return true;
            }
            exception(SysInfraResultEnum.VERIFY_ERROR);
        } finally {
            cacheService.del(key);
        }
        return false;
    }

    /**
     * 生成base64图片
     * @param image
     * @return
     */
    private String getBase64Images(BufferedImage image){
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",os);
        } catch (IOException e) {
            log.error("生成验证码异常！",e);
            exception(SysInfraResultEnum.VERIFY_CREATE_FAIL);
        }finally {
            if(os != null){
                os.close();
            }
        }
        return Base64.encode(os.toByteArray());
    }

}
