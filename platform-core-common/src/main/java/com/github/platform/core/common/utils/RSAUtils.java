package com.github.platform.core.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA加解密工具类
 */
@Slf4j
public class RSAUtils {

    private static final String RSA = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * 生成密钥对
     *
     * @param keySize 密钥长度（推荐2048或以上）
     * @return KeyPair 生成的密钥对
     * @throws NoSuchAlgorithmException 当RSA算法不可用时抛出
     */
    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
        keyPairGen.initialize(keySize);
        return keyPairGen.generateKeyPair();
    }
    /**
     * 公钥加密
     *
     * @param data 待加密的明文
     * @param publicKey 公钥
     * @return 加密后的密文（Base64编码）
     * @throws GeneralSecurityException 当加密失败时抛出
     */
    public static String encrypt(String data, String publicKey) throws GeneralSecurityException {
        return encrypt(data, getPublicKey(publicKey));
    }
    /**
     * 公钥加密
     *
     * @param data 待加密的明文
     * @param publicKey 公钥
     * @return 加密后的密文（Base64编码）
     * @throws GeneralSecurityException 当加密失败时抛出
     */
    public static String encrypt(String data, PublicKey publicKey) throws GeneralSecurityException {
        return base64Encode(processData(Cipher.ENCRYPT_MODE, data.getBytes(StandardCharsets.UTF_8), publicKey));
    }
    /**
     * 私钥解密
     *
     * @param encryptedData 加密后的密文（Base64编码）
     * @param privateKey 私钥
     * @return 解密后的明文
     * @throws GeneralSecurityException 当解密失败时抛出
     */
    public static String decrypt(String encryptedData, String privateKey) throws GeneralSecurityException {
        return decrypt(encryptedData, getPrivateKey(privateKey));
    }
    /**
     * 私钥解密
     *
     * @param encryptedData 加密后的密文（Base64编码）
     * @param privateKey 私钥
     * @return 解密后的明文
     * @throws GeneralSecurityException 当解密失败时抛出
     */
    public static String decrypt(String encryptedData, PrivateKey privateKey) throws GeneralSecurityException {
        byte[] decryptedBytes = processData(Cipher.DECRYPT_MODE, base64Decode(encryptedData), privateKey);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 统一处理加密和解密的逻辑
     *
     * @param mode 加密或解密模式
     * @param data 待处理的数据
     * @param key 使用的密钥
     * @return 处理后的字节数组
     * @throws GeneralSecurityException 当处理失败时抛出
     */
    private static byte[] processData(int mode, byte[] data, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(mode, key);
        return cipher.doFinal(data);
    }
    /**
     * 数据签名
     *
     * @param data 待签名的数据
     * @param privateKey 私钥
     * @return 签名后的数据（Base64编码）
     * @throws GeneralSecurityException 当签名失败时抛出
     */
    public static String sign(String data,String privateKey) throws GeneralSecurityException {
        return sign(data,getPrivateKey(privateKey));
    }

    /**
     * 数据签名
     *
     * @param data 待签名的数据
     * @param privateKey 私钥
     * @return 签名后的数据（Base64编码）
     * @throws GeneralSecurityException 当签名失败时抛出
     */
    public static String sign(String data, PrivateKey privateKey) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return base64Encode(signature.sign());
    }
    /**
     * 签名验证
     *
     * @param data 原始数据
     * @param signatureData 签名数据（Base64编码）
     * @param publicKey 公钥
     * @return 验证结果，true表示签名有效，false表示签名无效
     * @throws GeneralSecurityException 当验证失败时抛出
     */
    public static boolean verify(String data, String signatureData, String publicKey) throws GeneralSecurityException {
        return verify(data, signatureData, getPublicKey(publicKey));
    }

    /**
     * 签名验证
     *
     * @param data 原始数据
     * @param signatureData 签名数据（Base64编码）
     * @param publicKey 公钥
     * @return 验证结果，true表示签名有效，false表示签名无效
     * @throws GeneralSecurityException 当验证失败时抛出
     */
    public static boolean verify(String data, String signatureData, PublicKey publicKey) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return signature.verify(base64Decode(signatureData));
    }

    /**
     * Base64编码
     *
     * @param data 待编码的字节数组
     * @return 编码后的字符串
     */
    private static String base64Encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * Base64解码
     *
     * @param data 待解码的字符串
     * @return 解码后的字节数组
     */
    private static byte[] base64Decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    /**
     * 从Base64字符串加载公钥
     *
     * @param key 公钥字符串（Base64编码）
     * @return PublicKey 公钥对象
     * @throws GeneralSecurityException 当解析失败时抛出
     */
    public static PublicKey getPublicKey(String key) throws GeneralSecurityException {
        // 解码Base64字符串
        byte[] keyBytes = base64Decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(spec);
    }

    /**
     * 从Base64字符串加载私钥
     *
     * @param key 私钥字符串（Base64编码）
     * @return PrivateKey 私钥对象
     * @throws GeneralSecurityException 当解析失败时抛出
     */
    public static PrivateKey getPrivateKey(String key) throws GeneralSecurityException {
        byte[] keyBytes = base64Decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(spec);
    }
    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair(2048);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String strPublicKey = base64Encode(publicKey.getEncoded());
        String strPrivateKey = base64Encode(privateKey.getEncoded());
        System.out.println("Public Key: " + strPublicKey);
        System.out.println("privateKey Key: " + strPrivateKey);
         getPublicKey(strPublicKey);
        String data = "Hello, World!";
        String encryptedData = encrypt(data, publicKey);
        System.out.println("Encrypted Data: " + encryptedData);

        String decryptedData = decrypt(encryptedData, privateKey);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}
