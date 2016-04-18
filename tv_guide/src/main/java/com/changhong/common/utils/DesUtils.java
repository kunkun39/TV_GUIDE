/*
 * Copyright (c) 2007 IJO Technologies Ltd.
 * www.ijotechnologies.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * IJO Technologies ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with IJO Technologies.
 */
package com.changhong.common.utils;


import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;


/**
 * @author Jack Wang
 */
public class DesUtils {
    private final static String DES = "DES";
    private final static String KEY = "abcdefgh";

    public static void main(String[] args) throws Exception {
        String data = "18011538429";
        System.err.println(getEncString(data));

    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param KEY  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String getEncString(String data) {
        try {
            byte[] bt = encrypt(data.getBytes(), KEY.getBytes());
            String strs = new BASE64Encoder().encode(bt);
            return strs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param KEY  加密键byte数组
     * @return
     * @throws java.io.IOException
     * @throws Exception
     */
    public static String getDesString(String data) {
        try {
            if (data == null)
                return null;
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf, KEY.getBytes());
            return new String(bt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}