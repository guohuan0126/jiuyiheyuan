package com.duanrong.util.security;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiao on 2016/3/22.
 */

public class SHA1withRSA {

	//密钥生成类型
    private static final String KEY_ALGORITHM = "RSA";

    //加密类型
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    //密钥长度
    private static final int KEY_SIZE = 1024;

    /**
     * 获取签名
     * @param data
     * @param privateKey
     * @return
     */
    public static byte[] sign(byte[] data, byte[] privateKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        //转换私钥材料
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);

        //实例化秘钥工厂
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);

        //获取私钥对象
        PrivateKey pk = kf.generatePrivate(keySpec);

        //获取signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        //初始化 signature
        signature.initSign(pk);

        //更新 signature
        signature.update(data);

        //签名
        return signature.sign();
    }

    /**
     * 校验签名
     * @param sign
     * @param data
     * @param publicKey
     * @return
     */
    public static boolean verify(byte[] sign, byte[] data, byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        //转换公钥材料
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);

        //实例化秘钥工厂
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);

        //获取私钥对象
        PublicKey pk = kf.generatePublic(keySpec);

        //获取signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        //初始化 signature
        signature.initVerify(pk);

        //更新 signature
        signature.update(data);

        return signature.verify(sign);
    }
    
    /**
     * 初始化秘钥
     * @return
     */
    public static Map<String, byte[]> initKeyMap() throws NoSuchAlgorithmException {

        //实例化秘钥工厂
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化秘钥长度
        kpg.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair kp = kpg.generateKeyPair();
        //取的私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        //取的公钥
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        Map<String, byte[]> keyMap = new HashMap<>();
        keyMap.put("private_key", privateKey.getEncoded());
        keyMap.put("public_key", publicKey.getEncoded());
        return keyMap;
    }

}
