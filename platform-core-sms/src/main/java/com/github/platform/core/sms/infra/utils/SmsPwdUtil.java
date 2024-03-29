package com.github.platform.core.sms.infra.utils;

import com.github.platform.core.common.utils.EncryptUtil;
import com.github.platform.core.common.utils.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 短信密码工具
 * @author: yxkong
 * @date: 2022/6/27 5:04 PM
 * @version: 1.0
 */
public class SmsPwdUtil {
    public static SmsPwdUtil me = new SmsPwdUtil();
    public static synchronized SmsPwdUtil getInstance() {
        if(me != null){
            return me;
        }
        synchronized (me){
            if (me!= null){
                return me;
            }
            me = new SmsPwdUtil();
        }
        return me;
    }

    /**
     * 自动生成盐值，并返回加密后的密码
     * @param pwd
     * @return
     */
    public Pair<String,String> encode(String pwd){
        String salt = StringUtils.randomStr(4, 6);
        String encryptPwd = EncryptUtil.me.aesEncode(pwd, salt);
        return Pair.of(salt,encryptPwd);
    }

    /**
     * 解密密码
     * @param encryptPwd
     * @param salt
     * @return
     */
    public String decode(String encryptPwd,String salt){
        return EncryptUtil.me.aesDecode(encryptPwd,salt);
    }

}
