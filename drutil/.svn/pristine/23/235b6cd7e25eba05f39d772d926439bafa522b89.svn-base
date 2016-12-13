package com.duanrong.util.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

/**
 * 证书签名工具类
 */
public class CertificateCoder {

    //证书类型
    private static final String CERT_TYPE = "X.509";

    //密钥库类型
    private static final String KEY_STORE = "PKCS12";
    
    //密钥类型,此方式采用X.509证书
    //private static final String KEY_ALGORITHM = "RSA";

    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 获取证书
     * @param certificatePath
     * @return
     * @throws CertificateException
     * @throws IOException
     */
    public static Certificate getCertificate(String certificatePath) throws CertificateException, IOException {
        //实例化证书工厂
        CertificateFactory cf = CertificateFactory.getInstance(CERT_TYPE);
        //获取证书文件流
        FileInputStream is = new FileInputStream(certificatePath);
        //生成证书对象
        Certificate certificate = cf.generateCertificate(is);
        is.close();
        return certificate;
    }

    /**
     * 获取证书公钥
     * @param certificatePath
     * @return
     * @throws CertificateException
     * @throws IOException
     */
    public static PublicKey getPublicKey(String certificatePath) throws CertificateException, IOException {
        Certificate cf = getCertificate(certificatePath);
        PublicKey pk = cf.getPublicKey();
        return pk;
    }


    /**
     * 获取秘钥库
     * @param keyStorePath
     * @param password
     * @return
     * @throws Exception
     */
    public static KeyStore getKeyStore(String keyStorePath, String password) throws Exception {

        //实例化秘钥库
        KeyStore ks = KeyStore.getInstance(KEY_STORE);
        //获取秘钥文件流
        FileInputStream is = new FileInputStream(keyStorePath);
        //加载秘钥文件
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }

    /**
     * 获取私钥文件
     * @param keyStorePath
     * @param password
     * @return
     * @throws Exception
     */
    public static Certificate getCertificate(String keyStorePath, String password) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, password);
        Enumeration<String> enumeration = ks.aliases();
        String alias = enumeration.nextElement();
        return ks.getCertificate(alias);
    }

    /**
     * 获取证书私钥对象
     * @param keyStorePath
     * @param password
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String keyStorePath, String password) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, password);
        Enumeration<String> enumeration = ks.aliases();
        String alias = enumeration.nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, password.toCharArray());
        return pk;
    }

    /**
     * 获取证书私钥对象
     * @param keyStorePath
     * @param password
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String keyStorePath, String password) throws Exception {
        Certificate certificate = getCertificate(keyStorePath, password);
        PublicKey pk = certificate.getPublicKey();
        return pk;
    }

    /**
     * 获取签名
     * @param data
     * @return
     */
    public static byte[] sign(String data, String keyStorePath, String password) throws Exception {

        //获取私钥对象
        PrivateKey pk = getPrivateKey(keyStorePath, password);

        //获取signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        //初始化 signature
        signature.initSign(pk);

        //更新 signature
        signature.update(data.getBytes());

        //签名
        return signature.sign();
    }

    /**
     * 校验签名
     * @param sign
     * @param data
     * @return
     */
    public static boolean verify(String sign, String data, String keyStorePath, String password) throws Exception{

        //获取公钥对象
        PublicKey pk = getPublicKey(keyStorePath, password);

        //获取signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        //初始化 signature
        signature.initVerify(pk);

        //更新 signature
        signature.update(data.getBytes());

        return signature.verify(sign.getBytes());
    }

    /**
     * 校验签名
     * @param sign
     * @param data
     * @return
     */
    public static boolean verify(String sign, String data, String keyStorePath) throws Exception{

        //获取公钥对象
        PublicKey pk = getPublicKey(keyStorePath);

        //获取signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        //初始化 signature
        signature.initVerify(pk);

        //更新 signature
        signature.update(data.getBytes());

        return signature.verify(sign.getBytes());
    }


}
