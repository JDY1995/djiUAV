package com.architecture.common.util;

import android.text.TextUtils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES处理工具类
 */
public class DESUtils {

    private  byte[] IV = {2, 4, 7, 3, 1, 8, 6, 9};

    private  final String KEY = "6wjufDX2";

    private  final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    private IvParameterSpec zeroIv;

    private SecretKeySpec keySpec;

    private Cipher cipher;

    public DESUtils(){

        try {
            zeroIv = generateParameter(IV);//偏移量
            keySpec = generateKey(KEY); //生成密钥
            cipher = Cipher.getInstance(ALGORITHM_DES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密(结果为Base64字符串)
     * @param encryptString
     * @return
     */
    public  String encrypt(String encryptString) {
        if (TextUtils.isEmpty(encryptString)) {
            return "";
        }
        String str = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            str = Base64.encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 解密16进制字符串
     * @param decryptString
     * @return
     */
    public  String decrypt(String decryptString) {
        if (TextUtils.isEmpty(decryptString)) {
            return "";
        }
        String str = null;
        try {
            byte[] byteMi = Base64.decode(decryptString);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            str = new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 创建密钥
     * @param password
     **/
    public  SecretKeySpec generateKey( String password) {
        byte[] key = password.getBytes();
        return new SecretKeySpec(key, "DES");
    }

    /**
     * 创建偏移量
     * @param iv
     **/
    public  IvParameterSpec generateParameter(byte[] iv) {
        return new IvParameterSpec(iv);
    }
}
