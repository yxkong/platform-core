package com.github.platform.core.common.utils;

import com.github.platform.core.standard.util.Base64;
import com.github.platform.core.standard.util.MD5Utils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;


/**
 * @author yxkong
 * @Description: MD5\SHA哈希，AES、DES对称加密解密
 */
public class EncryptUtil {

    private final static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";
    public static final String SHA256 = "SHA-256";
    public static final String HmacMD5 = "HmacMD5";
    public static final String HmacSHA256 = "HmacSHA256";
    public static final String HmacSHA1 = "HmacSHA1";
    public static final String DES = "DES";
    public static final String AES = "AES";

    /**
     * 编码格式
     */
    public static final String CHARSET = "UTF-8";
    /**
     * DES
     */
    public static final int KEYSIZEDES = 0;
    /**
     * AES
     */
    public static final int KEYSIZEAES = 128;
    private static final EncryptUtil me = new EncryptUtil();

    private EncryptUtil() {
        //单例
    }

    public static EncryptUtil getInstance() {
        return me;
    }

    /**
     * 使用MessageDigest进行单向加密（无密码）
     *
     * @param str       明文
     * @param algorithm 加密算法
     * @return
     */
    private String messageDigest(String str, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = CHARSET == null ? str.getBytes() : str.getBytes(CHARSET);
            return base64(md.digest(resBytes));
        } catch (Exception e) {
            logger.error("EncryptUtil messageDigest is error", e);
        }
        return null;
    }

    /**
     * 使用KeyGenerator进行单向/双向加密（可设密码）
     *
     * @param str       明文
     * @param algorithm 加密算法
     * @param key       盐值
     * @return
     */
    private String keyGeneratorMac(String str, String algorithm, String key) {
        try {
            SecretKey sk = null;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = CHARSET == null ? key.getBytes() : key.getBytes(CHARSET);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(str.getBytes());
            return base64(result);
        } catch (Exception e) {
            logger.error("EncryptUtil keyGeneratorMac is error", e);
        }
        return null;
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     *
     * @param str       明文
     * @param algorithm 加密算法
     * @param key       盐值
     * @param keysize
     * @param isEncode  是否编码转换
     * @return
     */
    private String keyGeneratorEs(String str, String algorithm, String key, int keysize, boolean isEncode) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                byte[] keyBytes = CHARSET == null ? key.getBytes() : key.getBytes(CHARSET);
                secureRandom.setSeed(keyBytes);
                kg.init(secureRandom);
            } else if (key == null) {
                kg.init(keysize);
            } else {
                byte[] keyBytes = CHARSET == null ? key.getBytes() : key.getBytes(CHARSET);
                secureRandom.setSeed(keyBytes);
                kg.init(keysize, secureRandom);
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = CHARSET == null ? str.getBytes() : str.getBytes(CHARSET);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(str)));
            }
        } catch (Exception e) {
            logger.error("EncryptUtil keyGeneratorES is error", e);
        }
        return null;
    }

    private String base64(byte[] bytes) {
        return Base64.encode(bytes);
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 通用md5加密
     * @param text
     * @param salt
     * @return
     */
    public String md5(String text,String salt){
        //加密密码
        return md5(text,salt,false);
    }

    /**
     * md5加密
     * @param text 文本
     * @param salt 盐值
     * @param flag true 将一些特殊符号移除  false 返回t通用jie结果
     */
    public String md5(String text,String salt,Boolean flag){
        String md5Str = MD5Utils.md5Salt(text, salt);
        if (flag){
            return md5Str.replaceAll("/", "").replaceAll("\\\\", "").replaceAll("\\+", "").replaceAll("=", "");
        }
        return md5Str;
    }

    /**
     * md5加密文本，返回盐值(已将特殊f符号移除)
     * @param text 加密文本
     * @return 盐值和加密结果
     */
    public Pair<String,String> md5(String text){
        return md5(text,6);
    }


    /**
     * md5加密文本，返回盐值
     * @param text 加密文本
     * @param length 盐值长度
     */
    public Pair<String,String> md5(String text,Integer length){
        // 随机生成盐值（一个账户，落库以后盐值不变）
        String salt = StringUtils.randomStr(length);
        return Pair.of(salt,md5(text,salt,true));
    }

    /**
     * 校验md5 和md5密码值
     * @param salt
     * @param pwd
     * @param md5Pwd
     * @return
     */
    public boolean verifyPwd(String salt,String pwd,String md5Pwd){
        String newMd5Pwd = MD5Utils.md5Salt(pwd, salt);
        return StringUtils.equals(md5Pwd,newMd5Pwd);
    }

    /**
     *  文件hash 码获取加密
     * @param fis
     * @return
     * @throws Exception
     */
    public String md5FileHash(InputStream fis) {
        try {
             // 使用指定的哈希算法 (MD5, SHA-1, SHA-256)
            MessageDigest digest = MessageDigest.getInstance(MD5);
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            byte[] hashBytes = digest.digest();

            // 转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (Exception e){

        }
        return null;
    }

    /**
     * SHA1 加密
     *
     * @param str
     * @return
     */
    public String sha1(String str) {
        return messageDigest(str, SHA1);
    }

    /**
     * SHA1 加密
     *
     * @param str 加密字符串
     * @param key 盐值
     * @return
     */
    public String sha1(String str, String key) {
        return keyGeneratorMac(str, HmacSHA1, key);
    }

    /**
     * DES 加密
     *
     * @param str 明文
     * @param key
     * @return
     */
    @Deprecated
    public String desEncode(String str, String key) {
        return keyGeneratorEs(str, DES, key, KEYSIZEDES, true);
    }

    /**
     * DES 解密
     *
     * @param str
     * @param key
     * @return
     */
    @Deprecated
    public String desDecode(String str, String key) {
        return keyGeneratorEs(str, DES, key, KEYSIZEDES, false);
    }

    /**
     * AES 加密
     *
     * @param str
     * @param key
     * @return
     */
    public String aesEncode(String str, String key) {
        return keyGeneratorEs(str, AES, key, KEYSIZEAES, true);
    }

    /**
     * AES 解密
     *
     * @param str
     * @param key
     * @return
     */
    public String aesDecode(String str, String key) {
        return keyGeneratorEs(str, AES, key, KEYSIZEAES, false);
    }

    /**
     * 异或加密
     *
     * @param str
     * @param key
     * @return
     */
    public String xorEncode(String str, String key) {
        byte[] bs = str.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }

    /**
     * 异或加密
     *
     * @param str
     * @param key
     * @return
     */
    public String xorDecode(String str, String key) {
        byte[] bs = parseHexStr2Byte(str);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }

    /**
     * 异或加密
     *
     * @param str
     * @param key
     * @return
     */
    public int xor(int str, String key) {
        return str ^ key.hashCode();
    }

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("D:\\test.txt"));
            String s = EncryptUtil.getInstance().md5FileHash(fileInputStream);
            System.out.printf(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
